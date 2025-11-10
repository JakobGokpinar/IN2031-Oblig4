package no.uio.aeroscript.runtime;

import no.uio.aeroscript.ast.stmt.Execution;
import no.uio.aeroscript.type.Memory;
import no.uio.aeroscript.error.LowBatteryException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/*
 * Read-Eval-Print Loop (REPL) for interactive AeroScript program execution.
 * 
 * Provides a command-line interface for users to:
 * - Trigger message events (message <name>)
 * - View system state and statistics (info)
 * - Display available commands (help)
 * - Exit the program gracefully (exit)
 * 
 * The REPL allows users to manually trigger events defined in the AeroScript program,
 * such as starting missions or invoking specific execution modes. It maintains references
 * to the heap, listeners, and the initial execution mode.
 * 
 * Commands are executed and timed, with results displayed back to the user. The REPL
 * continues running until explicitly terminated via the 'exit' command or when an
 * emergency landing completes.
 */

class Command {
    private final String name;
    private final REPL repl;
    private final Function<String, Boolean> command;
    private final String help;
    private final boolean requiresParameter;
    private final String parameterHelp;

    public Command(String name, REPL repl, Function<String, Boolean> command, String help, boolean requiresParameter, String parameterHelp) {
        this.name = name;
        this.repl = repl;
        this.command = command;
        this.help = help;
        this.requiresParameter = requiresParameter;
        this.parameterHelp = parameterHelp;
    }

    public String getName() {
        return name;
    }
    public String getHelp() {
        return help;
    }
    public boolean isRequiresParameter() {
        return requiresParameter;
    }
    public String getParameterHelp() {
        return parameterHelp;
    }
    public boolean execute(String param) {
        if (requiresParameter && Objects.equals(param, "")) {
            System.out.println(name + " requires a parameter: " + parameterHelp);
            return false;
        }

        return command.apply(param);
    }
}


public class REPL {

    private final HashMap<Memory, Object> heap;
    private final HashMap<String, Command> commands = new HashMap<>();
    private final Map<String, Runnable> listeners;
    private final Execution initialExecution;
    private boolean terminating = false;

    public REPL(HashMap<Memory, Object> heap, Map<String, Runnable> listeners, Execution initialExecution) {
        this.heap = heap;
        this.listeners = listeners;
        this.initialExecution = initialExecution;
        initCommands();
    }

    public boolean isTerminating() {
        return terminating;
    }

    private void printRepl(String str) {
        System.out.println("MO-out> " + str);
    }

    public boolean command(String str, String param) {
        LocalTime start = LocalTime.now();
        boolean result;

        if (str.equals("help")) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("  AVAILABLE COMMANDS");
            System.out.println("=".repeat(60));
            
            for (Command cmd : commands.values()) {
                if (!cmd.getName().equals("help")) {  // Show help last
                    System.out.println("\n  " + cmd.getName());
                    System.out.println("    " + cmd.getHelp());
                    if (!cmd.getParameterHelp().isEmpty()) {
                        System.out.println("    Usage: " + cmd.getName() + " " + cmd.getParameterHelp());
                    }
                }
            }
            
            System.out.println("\n  help");
            System.out.println("    Display this help message");
            System.out.println("\n" + "=".repeat(60) + "\n");
            result = false;
        } else {
            Command cmd = commands.get(str);

            if (cmd == null) {
                printRepl("Unknown command: " + str + ". Type \"help\" to get a list of available commands");
                result = false;
            } else {
                try {
                    cmd.execute(param);
                    result = true;
                } catch (LowBatteryException e) {
                    printRepl("Battery depleted: " + e.getMessage());
                    result = false;
                } catch (Exception e) {
                    printRepl("Error: " + e.getMessage());
                    e.printStackTrace();
                    result = false;
                }
            }
        }

        float time = Duration.between(start, LocalTime.now()).toMillis() / 1000f;
        printRepl("Command executed in " + time + " seconds");

        return result;
    }

    private void initCommands() {
        // for command "message" we get the message passed as parameter, we need to add the trigger for the listener
        commands.put("message", new Command("message", this, (param) -> {
            Runnable listener = listeners.get(param);
            if (listener != null) {
                this.initialExecution.receiveMessage(param);
            } else {
                printRepl("No listener for message: " + param);
            }
            return true;
        }, "Trigger a message event", true, "<message_name>"));
        
        commands.put("info", new Command("info", this, (param) -> {
            HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);
            Map<String, String> messages = (Map<String, String>) heap.get(Memory.MESSAGES);
            Map<String, String> reactions = (Map<String, String>) heap.get(Memory.REACTIONS);
            Map<String, Execution> executions = (Map<String, Execution>) heap.get(Memory.EXECUTIONS);
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("  SYSTEM STATE");
            System.out.println("=".repeat(60));
            
            System.out.println("\n📍 Current Status:");
            System.out.println("  Position:  " + vars.get("current position"));
            System.out.println("  Altitude:  " + String.format("%.2f", vars.get("altitude")) + " meters");
            System.out.println("  Battery:   " + String.format("%.2f", vars.get("battery level")) + "%");
            System.out.println("  Distance:  " + String.format("%.2f", vars.get("distance travelled")) + " meters");
            
            System.out.println("\n📨 Registered Messages:");
            if (messages == null || messages.isEmpty()) {
                System.out.println("  (none)");
            } else {
                messages.forEach((msg, mode) -> 
                    System.out.println("  " + msg + " → " + mode));
            }
            
            System.out.println("\n⚡ Registered Reactions:");
            if (reactions == null || reactions.isEmpty()) {
                System.out.println("  (none)");
            } else {
                reactions.forEach((event, mode) -> 
                    System.out.println("  " + event + " → " + mode));
            }
            
            System.out.println("\n🎯 Available Modes:");
            if (executions != null) {
                executions.keySet().forEach(name -> 
                    System.out.println("  • " + name));
            }
            
            System.out.println("\n" + "=".repeat(60) + "\n");
            
            return true;
        }, "Display system state and statistics", false, ""));

        commands.put("exit", new Command("exit", this, (param) -> {
            terminate();
            return true;
        }, "Exit the REPL and terminate program", false, ""));

        commands.put("help", new Command("help", this, (param) -> false, "Display this help message", false, ""));

    }

    public void terminate() {
        System.out.println("Terminating the REPL");
        this.terminating = true;
    }
}

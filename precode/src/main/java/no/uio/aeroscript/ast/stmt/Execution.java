package no.uio.aeroscript.ast.stmt;

import no.uio.aeroscript.type.Memory;

import java.util.*;

import no.uio.aeroscript.error.LowBatteryException;

/*
 * Represents an executable mode in AeroScript programs.
 * 
 * An Execution encapsulates some block of statements that will be executed sequentially,
 * with support for:
 * - Mode transitions (-> NextMode)
 * - Event-driven reactions (on message/low battery)
 * - Emergency procedures (battery depletion handling)
 * - Nested mode execution
 * 
 * Executions maintain their own statement stack and can trigger other executions
 * based on messages or system events. When battery drops below 20%, the low battery
 * reaction is triggered if defined, otherwise the program terminates.
 */

public class Execution extends Statement {
    private final String name;
    private final List<Statement> statements;
    private final Map<String, Execution> nestedExecutions;
    private final HashMap<String, Runnable> listeners;
    private final HashMap<Memory, Object> heap;
    private final Stack<Statement> stack;
    private final Stack<Statement> emergencyStack;
    private final List<String> declare = new ArrayList<>();
    private final boolean toExec;
    private boolean messageReceivingEnabled = true;

    private String child = null;

    
    public Execution(String name, List<Statement> statements,
                     Map<String, Execution> nestedExecutions,
                     HashMap<Memory, Object> heap,
                     Stack<Statement> stack,
                     HashMap<String, Runnable> listeners,
                     boolean toExec) {
        this.name = name;
        this.statements = statements;
        this.nestedExecutions = nestedExecutions;
        this.heap = heap;
        this.stack = stack;
        this.listeners = listeners;
        this.emergencyStack = new Stack<>();
        this.toExec = toExec;
    }

    public String getName() {
        return name;
    }
    public List<Statement> getStatements() {
        return statements;
    }
    public Map<String, Execution> getNestedExecutions() {
        return nestedExecutions;
    }
    public void addListener(String message, Runnable listener) {
        listeners.put(message, listener);
    }
    public List<String> getDeclare() {
        return declare;
    }
    public void addDeclare(String variable) {
        declare.add(variable);
    }
    public boolean getExec() {
        return toExec;
    }
    public void addStatements(List<Statement> statements) {
        this.stack.addAll(statements);
    }
    public String getChild() {
        return child;
    }
    public void setChild(String child) {
        this.child = child;
    }

    public void receiveMessage(String message) {

        if (!messageReceivingEnabled) {
            return;
        }
        Map<String, String> messages = (Map<String, String>) this.heap.get(Memory.MESSAGES);
        Map<String, String> triggers = (Map<String, String>) this.heap.get(Memory.REACTIONS);
        if(messages != null && messages.containsKey(message)) {
            Map<String, Execution> executions = (Map<String, Execution>) this.heap.get(Memory.EXECUTIONS);
            if(executions != null) {
                Execution execution = executions.get(messages.get(message));
                this.stack.addAll(execution.getStatements());
                this.child = execution.getChild();  // ADD THIS LINE

                if (!execution.getDeclare().isEmpty()) {
                    for (String variable : execution.getDeclare()) {
                        this.stack.addAll(executions.get(variable).getStatements());
                    }
                }
            }
        } else if (triggers != null && triggers.containsKey(message)) {
            Map<String, Execution> executions = (Map<String, Execution>) this.heap.get(Memory.EXECUTIONS);
            if(executions != null) {
                assert heap.get(Memory.VARIABLES) instanceof HashMap;
                HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);
                Execution execution = executions.get(triggers.get(message));

                // If battery low only execute the emergency execution
                if (message.equals("low battery")) {
                    messageReceivingEnabled = false;
                    emergencyStack.addAll(execution.getStatements());
                }
                this.stack.addAll(0, execution.getStatements());

                if (!execution.getDeclare().isEmpty()) {
                    for (String variable : execution.getDeclare()) {
                        this.stack.addAll(0, executions.get(variable).getStatements());
                    }
                }
            }
        }

        execute();
    }

    @Override
    public void execute() {
        assert heap.get(Memory.VARIABLES) instanceof HashMap;
        HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);

        // Handle emergency stack first
        if (!emergencyStack.isEmpty()) {
            System.out.println("\n" + "!".repeat(60));
            System.out.println("⚠️  EMERGENCY LANDING PROCEDURE");
            System.out.println("!".repeat(60) + "\n");
            
            try {
                Iterator<Statement> emergencyIterator = emergencyStack.iterator();
                while (emergencyIterator.hasNext()) {
                    Statement statement = emergencyIterator.next();
                    statement.setHeap(heap);
                    statement.execute();
                    emergencyIterator.remove();
                }
                emergencyStack.clear();
                
                System.out.println("\n" + "!".repeat(60));
                System.out.println("Emergency landing completed");
                System.out.println("!".repeat(60) + "\n");
            //Check if drone crashes while trying to emergency land
            } catch (RuntimeException e) {
                System.out.println("\n" + "💥".repeat(60));
                System.out.println("🚨 CRITICAL FAILURE: " + e.getMessage());
                System.out.println("🚨 DRONE CRASHED - INSUFFICIENT POWER FOR EMERGENCY LANDING");
                System.out.println("💥".repeat(60) + "\n");
            }
            System.exit(1);
        }

        // Execute statements
        Iterator<Statement> iterator = stack.iterator();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
            
            if (statement instanceof Execution execStatement) {
                if (!execStatement.toExec) {
                    iterator.remove();
                    continue;
                }
                if (!execStatement.getDeclare().isEmpty()) {
                    for (String variable : execStatement.getDeclare()) {
                        this.stack.addAll(execStatement.getNestedExecutions().get(variable).getStatements());
                    }
                }
            }
            
            try {
                statement.setHeap(heap);
                statement.execute();
                iterator.remove();
            } catch (LowBatteryException e) {
                iterator.remove();
                
                // Print ONE clear warning
                System.out.println("\n" + "!".repeat(60));
                System.out.println("⚠️  CRITICAL: Battery level at " + String.format("%.2f", e.getBatteryLevel()) + "%");
                System.out.println("!".repeat(60));
                System.out.println("Initiating emergency landing procedure...\n");
                
                stack.clear();  // Clear remaining actions
                receiveMessage("low battery");  // Trigger emergency
                return;
            }
        }
        
        // Handle transitions
        if (stack.isEmpty() && this.child != null) {
            Map<String, Execution> executions = (Map<String, Execution>) heap.get(Memory.EXECUTIONS);
            if (executions != null && executions.containsKey(this.child)) {
                System.out.println("\n" + "─".repeat(60));
                System.out.println("  Transitioning to: " + this.child);
                System.out.println("─".repeat(60) + "\n");
                
                Execution nextExec = executions.get(this.child);
                this.stack.addAll(nextExec.getStatements());
                this.child = nextExec.getChild();
                execute();
            }
        }
    }
}

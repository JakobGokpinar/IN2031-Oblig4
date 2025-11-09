package no.uio.aeroscript;

import no.uio.aeroscript.antlr.AeroScriptLexer;
import no.uio.aeroscript.antlr.AeroScriptParser;
import no.uio.aeroscript.ast.stmt.Execution;
import no.uio.aeroscript.runtime.Interpreter;
import no.uio.aeroscript.runtime.TypeCheck;
//...
import no.uio.aeroscript.runtime.REPL;
//...
import no.uio.aeroscript.type.Memory;
import no.uio.aeroscript.type.Point;
import no.uio.aeroscript.ast.stmt.Statement;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
//...
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
//...

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.jline.terminal.dumb", "true");
        HashMap<Memory, Object> heap = new HashMap<>();
        Stack<Statement> stack = new Stack<>();
        float batteryLevel;
        Point initialPosition;

        if (args.length < 1) {
            System.err.println("Usage: java -jar aeroscript.jar <path to file> [-b <battery level>] [-p <x> <y>]");
            System.exit(1);
        }
        String path = args[0];
        // check if the user used the option -b to set the battery level
        if (args.length > 1 && args[1].equals("-b")) {
            batteryLevel = Float.parseFloat(args[2]);
        } else if (args.length > 3 && args[4].equals("-b")) {
            batteryLevel = Float.parseFloat(args[5]);
        } else {
            batteryLevel = 100.0f;
        }

        // check if the user used the option -p to set the initial position
        if (args.length > 1 && args[1].equals("-p")) {
            initialPosition = new Point(Float.parseFloat(args[2]), Float.parseFloat(args[3]));
        } else if (args.length > 3 && args[3].equals("-p")) {
            initialPosition = new Point(Float.parseFloat(args[4]), Float.parseFloat(args[5]));
        } else {
            initialPosition = new Point(0.0f, 0.0f);
        }

        float initialX = initialPosition.getX();
        float initialY = initialPosition.getY();
        float initialZ = 0.0f;

        HashMap<Memory, HashMap<String, Object>> variables = new HashMap<>();
        variables.put(Memory.VARIABLES, new HashMap<>());
        HashMap<String, Object> vars = variables.get(Memory.VARIABLES);

        vars.put("initial position", initialPosition);
        vars.put("current position", initialPosition);
        vars.put("battery level", batteryLevel);
        vars.put("initial battery level", batteryLevel);
        vars.put("distance travelled", 0.0f);
        vars.put("altitude", 0.0f);

        heap.put(Memory.VARIABLES, vars);
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            try {
                // Read all bytes of the file for parsing using the interpreter.
                AeroScriptLexer lexer = new AeroScriptLexer(CharStreams.fromString(content));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                AeroScriptParser parser = new AeroScriptParser(tokens);
                AeroScriptParser.ProgramContext programContext = parser.program();

                Interpreter interpreter = new Interpreter(heap, stack);
                TypeCheck typeChecker = new TypeCheck();
                typeChecker.visitProgram(programContext);

                System.out.println();
                System.out.println("Initial position: " + initialPosition);
                System.out.println("Initial Battery level: " + vars.get("initial battery level"));
                System.out.println("Start altitude: " + vars.get("altitude") + "\n");

                System.out.println("==== Parsing and Building AST ====");
                ArrayList<Execution> executions = interpreter.visitProgram(programContext);
                System.out.println("Successfully parsed " + executions.size() + " modes\n");

                

                System.out.println("\n==== Initial Execution Complete ====\n");

                // New for Oblig3: REPL Integration ==========       
                Execution firstExecution = interpreter.getFirstExecution();         
                if (firstExecution == null) {
                    System.err.println("Warning: No starting mode found (no mode with leading ->)");
                }

                REPL repl = new REPL(heap, interpreter.getListeners(), firstExecution);
                System.out.println("Welcome to the AeroScript REPL!");
                System.out.println("=== Entering REPL Mode ===");
                System.out.println("Type 'help' for available commands");
                System.out.println("Type 'message <name>' to trigger events");
                System.out.println("Type 'exit' to quit\n");

                while(!repl.isTerminating()) {
                    try {
                        System.setProperty("org.jline.terminal", "jline.UnsupportedTerminal");
                        LineReader reader = LineReaderBuilder.builder().build();
                        String next = reader.readLine("MO> ");
                        if (next == null) {  // ADD THIS CHECK
                            break;           // Exit if no input (EOF/Ctrl+D)
                        }
                        
                        if (next.trim().isEmpty()) {
                            continue;
                        }
                        
                        String[] splits = next.trim().split(" ", 2);
                        String command = splits[0];
                        String param = splits.length == 1 ? "" : splits[1].trim();
                        
                        repl.command(command, param);
                    } catch (Exception e) {
                        System.err.println("Error in REPL: " + e.getMessage());
                        break;
                    }
                    
                }
                System.out.println("Execution complete!");

            } catch (ParseCancellationException e) {
                System.err.println("Parser error: " + e.getMessage());
                System.out.println();
            }
            
        }  catch (Exception e) {
            System.err.println("\nWe got error, terminating execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n=== Final Results ===\n");
            System.out.println("Initial position: " + vars.get("initial position"));
            System.out.println("Initial battery: " + vars.get("initial battery level") + "%");
            System.out.println("Final position: " + vars.get("current position"));
            System.out.println("Final battery: " + vars.get("battery level") + "%");
            System.out.println("Final altitude: " + vars.get("altitude") + " meters");
            System.out.println("Distance travelled: " + vars.get("distance travelled") + " meters");
            System.out.println("\nExecution complete!");
        }
    }
}
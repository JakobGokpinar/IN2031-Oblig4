package no.uio.aeroscript.runtime;

import no.uio.aeroscript.antlr.AeroScriptLexer;
import no.uio.aeroscript.antlr.AeroScriptParser;
import no.uio.aeroscript.ast.stmt.Execution;
import no.uio.aeroscript.ast.stmt.Statement;
import no.uio.aeroscript.type.Memory;
import no.uio.aeroscript.type.Point;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


class InterpreterTest {

    private HashMap<Memory, Object> heap;
    private Stack<Statement> stack;

    private void initInterpreter() {
        this.heap = new HashMap<>();
        this.stack = new Stack<>();
        HashMap<Memory, HashMap<String, Object>> variables = new HashMap<>();
        variables.put(Memory.VARIABLES, new HashMap<>());
        HashMap<String, Object> vars = variables.get(Memory.VARIABLES);

        float batteryLevel = 100;
        int initialZ = 0;
        Point initialPosition = new Point(0, 0);

        vars.put("initial position", initialPosition);
        vars.put("current position", initialPosition);
        vars.put("altitude", initialZ);
        vars.put("initial battery level", batteryLevel);
        vars.put("battery level", batteryLevel);
        vars.put("battery low", false);
        vars.put("distance travelled", 0.0f);
        vars.put("initial execution", null);

        heap.put(Memory.VARIABLES, vars);
    }

    private AeroScriptParser.ExpressionContext parseExpression(String expression) {
        AeroScriptLexer lexer = new AeroScriptLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AeroScriptParser parser = new AeroScriptParser(tokens);
        return parser.expression();
    }



    // ============ OBLIG 1 & 2: Expression Evaluation ============

    @Test
    void visitExpression() {
        initInterpreter();
        Interpreter interpreter = new Interpreter(this.heap, this.stack);

        assertEquals(5.0f, Float.parseFloat(interpreter.visitExpression(parseExpression("2 + 3")).evaluate().toString()));
        assertEquals(-1.0f, Float.parseFloat(interpreter.visitExpression(parseExpression("2 - 3")).evaluate().toString()));
        assertEquals(6.0f, Float.parseFloat(interpreter.visitExpression(parseExpression("2 * 3")).evaluate().toString()));
        assertEquals(-1, Float.parseFloat(interpreter.visitExpression(parseExpression("-- 1")).evaluate().toString()));
    }


    // ============ OBLIG 2: Modes and Actions ============

    @Test
    void getFirstExecution() {
        initInterpreter();
        Interpreter interpreter = new Interpreter(this.heap, this.stack);

        assertNull(interpreter.getFirstExecution());
        Execution testExec = new Execution("Test", new ArrayList<>(), new HashMap<>(), heap, stack, new HashMap<>(), true);
        interpreter.setFirstExecution(testExec);
        assertEquals("Test", interpreter.getFirstExecution().getName());
    }

    @Test
    void getPosition() {
        initInterpreter();
        Interpreter interpreter = new Interpreter(this.heap, this.stack);

        Point pos = interpreter.getPosition();
        assertEquals(0.0f, pos.getX());
        assertEquals(0.0f, pos.getY());
    }

    @Test
    void getDistanceTravelled() {
        initInterpreter();
        Interpreter interpreter = new Interpreter(this.heap, this.stack);

        assertEquals(0.0f, interpreter.getDistanceTravelled());
    }

    @Test
    void getBatteryLevel() {
        initInterpreter();
        Interpreter interpreter = new Interpreter(this.heap, this.stack);

        assertEquals(100.0f, interpreter.getBatteryLevel());
    }

    @Test
    void testModeTransitions() {
        initInterpreter();
        Interpreter interpreter = new Interpreter(this.heap, this.stack);
        
        String program = """
            -> First {
                ascend by 10
            } -> Second
            Second {
                descend by 5
            }
            """;
        
        AeroScriptParser.ProgramContext ctx = parseProgram(program);
        interpreter.visitProgram(ctx);
        
        Execution firstExec = interpreter.getFirstExecution();
        assertEquals("First", firstExec.getName());
        assertEquals("Second", firstExec.getChild());
    }


    // ============ OBLIG 3: Full Program Execution ============
    
    /*@Test
    void visitProgram() {
        initInterpreter();
        Interpreter interpreter = new Interpreter(this.heap, this.stack);

        try {
            String content = new String(Files.readAllBytes(Paths.get("src/test/resources/program2.aero")));
            AeroScriptLexer lexer = new AeroScriptLexer(CharStreams.fromString(content));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            AeroScriptParser parser = new AeroScriptParser(tokens);
            AeroScriptParser.ProgramContext ctx = parser.program();
            
            var executions = interpreter.visitProgram(ctx);
            
            assertNotNull(interpreter.getFirstExecution());
            assertEquals(5, executions.size()); // Update count based on your program.aero
        } catch (Exception e) {
            fail("Failed to parse program: " + e.getMessage());
        }
    }*/

    
}

package no.uio.aeroscript.runtime;

import no.uio.aeroscript.antlr.AeroScriptLexer;
import no.uio.aeroscript.antlr.AeroScriptParser;
import no.uio.aeroscript.error.TypeError;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TypeCheckTest {

    // ============ OBLIG 4: Type Checking ============
    
    private void typeCheck(String code) {
        AeroScriptLexer lexer = new AeroScriptLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AeroScriptParser parser = new AeroScriptParser(tokens);
        AeroScriptParser.ProgramContext programContext = parser.program();
        
        TypeCheck typeChecker = new TypeCheck();
        typeChecker.visitProgram(programContext);
    }
    
    // Valid programs - should not throw error

    @Test
    void testValidNumAddition() {
        assertDoesNotThrow(() -> typeCheck(
            "-> Test { ascend by (10 + 20) }"
        ));
    }
    
    @Test
    void testValidPointAddition() {
        assertDoesNotThrow(() -> typeCheck(
            "-> Test { move to point((10 + 5), (20 + 5)) }"
        ));
    }
    
    @Test
    void testValidPointMultiplication() {
        assertDoesNotThrow(() -> typeCheck(
            "-> Test { move to point((5 * 2), (10 * 3)) }"
        ));
    }
    
    @Test
    void testValidMoveToPoint() {
        assertDoesNotThrow(() -> typeCheck(
            "-> Test { move to point(50, 50) }"
        ));
    }
    
    @Test
    void testInvalidDoubleNegationOfPoint() {
        assertDoesNotThrow(() -> typeCheck(
            "-> Test { move to point(--10, --20) }"
        ));
        // This is actually VALID - negating Nums
    }


    // Invalid programs - should throw TypeError

    @Test
    void testInvalidAscendWithPoint() {
        assertThrows(TypeError.class, () -> typeCheck(
            "-> Test { ascend by point(10, 20) }"
        ));
    }
    
    @Test
    void testInvalidPointComponents() {
        assertThrows(TypeError.class, () -> typeCheck(
            "-> Test { move to point(10, point(5, 5)) }"
        ));
    }
    
    @Test
    void testInvalidAddition() {
        assertThrows(TypeError.class, () -> typeCheck(
            "-> Test { ascend by (10 + point(5, 5)) }"
        ));
    }
    
    @Test
    void testInvalidPointMultiplication() {
        assertThrows(TypeError.class, () -> typeCheck(
            "-> Test { move to point((point(1,2) * point(3,4)), 5) }"
        ));
    }
    
    // Test that random requires Num components
    @Test
    void testInvalidRangeWithPointComponents() {
        assertThrows(TypeError.class, () -> typeCheck(
            "-> Test { ascend by random[point(0,0), 100] }"
        ));
    }

    
    @Test
    void testInvalidSpeedWithPoint() {
        assertThrows(TypeError.class, () -> typeCheck(
            "-> Test { ascend by 10 at speed point(5, 5) }"
        ));
    }
    
}
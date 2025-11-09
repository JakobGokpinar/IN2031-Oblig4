package no.uio.aeroscript.runtime;

import no.uio.aeroscript.antlr.AeroScriptBaseVisitor;
import no.uio.aeroscript.antlr.AeroScriptParser;
import no.uio.aeroscript.error.TypeError;

// Return a String representing the type: "Num", "Point", or "Range"

/*
Pitfalls to Avoid

1. Don't evaluate expressions - just check their types!
2. Remember parentheses - (expr) has the same type as expr
3. Random expressions - random is Num, random[...] needs a Range
4. Handle all operators - +, -, * each have their own rules
*/
public class TypeCheck extends AeroScriptBaseVisitor<String> {

	//Expression checking
	@Override
	public String visitPlusExp(AeroScriptParser.PlusExpContext ctx) {
		String leftType = visit(ctx.left);
		String rightType = visit(ctx.right);

		if (leftType.equals("Num") && rightType.equals("Num")) {
			return "Num"; //Valid: Num + Num -> Num
		} else if (leftType.equals("Point") && rightType.equals("Point")) {
			return "Point"; //Valid: Point + Point -> Point
		} else {
			throw new TypeError("Cannot add: " + leftType + " and " + rightType);
		}
	}


	@Override
	public String visitNumExp(AeroScriptParser.NumExpContext ctx) {
		return "Num"; //Numbers are always Num
	}

	@Override
	public String visitPointExp(AeroScriptParser.PointExpContext ctx) {
		String xType = visit(ctx.point().left);
		String yType = visit(ctx.point().right);

		if (!xType.equals("Num") || !yType.equals("Num")) {
			throw new TypeError("Points components must be Num, got ( " + xType + ", " + yType + ")");
		}

		return "Point";
	}

	@Override
	public String visitNegExp(AeroScriptParser.NegExpContext ctx) {
		String type = visit(ctx.expression()); //What is type here?? What is expression return here?

		if (type.equals("Range")) {
			throw new TypeError("Cannot negate a Range");
		}
		return type;
	}

	@Override
	public String visitTimesEsp(AeroScriptParser.TimesExpContext ctx) {
		String leftType = visit(ctx.left); //What is left and right return here?
		String rightType = visit(ctx.right);
		/*
		Cases:
		1. Num * Num -> Num
		2. Point * Num -> Point
		3. Num * Point -> Point
		*/
		if (leftType.equals("Num") && rightType.equals("Num")) {
        	return "Num";
    	} else if (leftType.equals("Point") && rightType.equals("Num")) {
        	return "Point";
    	} else if (leftType.equals("Num") && rightType.equals("Point")) {
        	return "Point";
    	} else {
    		throw new TypeError("Unvalid arguments passed to Times expression");
    	}
	}


	//Statement checking
	/*
		1. Statements return null
	*/
	@Override
	public String visitAscendStatement(AeroScriptParser.AscendStatementContext ctx) {
		// Statements don't return a type, but we check their sub-expressions
		String exprType = visit(ctx.expression()); //is it 'ascend by 20'?

		if (!exprType.equals("Num")) {
        	throw new TypeError("ascend by requires Num, got " + exprType);
    	}
    	return null;
	}


	//Program and Mode checking
	/*
		1. Programs and modes return null
	*/
	@Override
	public String visitProgram(AeroScriptParser.ProgramContext ctx) {
		//check all modes
		for (var modeCtx: ctx.mode()) {
			visit(modeCtx);
		}
		return null;
	}

	@Override
	public String visitExecution(AeroScriptParser.ExecutionContext ctx) {
		//check all statments in this mode
		for (var stmtCtx: ctx.statement()) {
			visit(stmtCtx);
		}
		return null;
 	}


}
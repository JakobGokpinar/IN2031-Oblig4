package no.uio.aeroscript.runtime;

import no.uio.aeroscript.antlr.AeroScriptBaseVisitor;
import no.uio.aeroscript.antlr.AeroScriptParser;
import no.uio.aeroscript.error.TypeError;


/*
Pitfalls to Avoid

1. Don't evaluate expressions - just check their types!
2. Remember parentheses - (expr) has the same type as expr
3. Random expressions - random is Num, random[...] needs a Range
4. Handle all operators - +, -, * each have their own rules
*/

/*
	Returns: "Num", "Point", or "Range"
    Throws: TypeError when type checking fails
*/
public class TypeCheck extends AeroScriptBaseVisitor<String> {


	/*
	- Expression checking
	- Expressions will return Num, Point or Range, or will throw error
	*/
	@Override
	public String visitNegExp(AeroScriptParser.NegExpContext ctx) {
		//--2
		String type = visit(ctx.expression());

		if (type.equals("Range")) {
			throw new TypeError("Cannot negate a Range");
		}
		return type;
	}

	@Override
	public String visitNumExp(AeroScriptParser.NumExpContext ctx) {
		return "Num";
	}

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
	public String visitTimesExp(AeroScriptParser.TimesExpContext ctx) {
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
	

	@Override
	public String visitPointExp(AeroScriptParser.PointExpContext ctx) {
		String xType = visit(ctx.point().left);
		String yType = visit(ctx.point().right);

		if (!xType.equals("Num") || !yType.equals("Num")) {
			throw new TypeError("Points components must be Num, got ( " + xType + ", " + yType + ")");
		}

		return "Point";
	}

	

	



	/*
	- Statement and Action checking
	- Statements will return null
	- construction:
		statement: action | reaction;
		action: (acDock | acMove | acTurn | acAscend | acDescend)
    			(FOR expression SECONDS | AT SPEED expression)?;
	*/
	@Override
	public String visitStatement(AeroScriptParser.StatementContext ctx) {
		if (ctx.action() != null) {
			visit(ctx.action());
		}
		// Reactions don't need type checking, they just refer mode names
		return null;
	}

	@Override
	public String visitAction(AeroScriptParser.ActionContext ctx) {
		if (ctx.SECONDS() != null) {
			String timeType = visit(ctx.expression()); //visitExpression
			if (!timeType.equals("Num")) {
				throw new TypeError("'for...seconds' requires Num, got " + timeType);
			}
		}

		if (ctx.SPEED() != null) {
			String speedType = visit(ctx.expression());
			if (!speedType.equals("Num")) {
				throw new TypeError("'at speed' requires Num, got " + speedType);
			}
		}

		//check the spesific action
		if (ctx.acAscend() != null) {
			visit(ctx.acAscend());
		} else if (ctx.acDescend() != null) {
			visit(ctx.acDescend());
		} else if (ctx.acTurn() != null) {
			visit(ctx.acTurn());
		} else if (ctx.acMove() != null) {
			visit(ctx.acMove());
		} else if (ctx.acDock() != null) {
			visit(ctx.acDock());
		}
		return null;
	}

	@Override
	public String visitAcAscend(AeroScriptParser.AcAscendContext ctx) {
		// Statements don't return a type, but we check their sub-expressions
		String type = visit(ctx.expression());

		if (!type.equals("Num")) {
        	throw new TypeError("ascend by requires Num, got " + type);
    	}
    	return null;
	}

	@Override
	public String visitAcDescend(AeroScriptParser.AcDescendContext ctx) {
		if (ctx.GROUND() != null) {
			// "descend to ground" - no expression to check
			return null;
		}
		String type = visit(ctx.expression());
		if (!type.equals("Num")) {
        	throw new TypeError("descend by requires Num, got " + type);
    	}
    	return null;
	}

	@Override
	public String visitAcMove(AeroScriptParser.AcMoveContext ctx) {
		//MOVE (TO POINT point | BY NUMBER);
		if (ctx.POINT() != null) {
			String pointType = visitPoint(ctx.point()); // visitPoint will throw error if components aren't Num
		}
		// else: move by NUMBER - NUMBER is already validated by lexer, so no need to check it here
		return null;
	}

	@Override
	public String visitAcTurn(AeroScriptParser.AcTurnContext ctx) {
		// TURN (RIGHT | LEFT)? BY expression;
		String type = visit(ctx.expression());
		if (!type.equals("Num")) {
			throw new TypeError("turn by requires Num, got " + type);
		}
		return null;
	}

	@Override
	public String visitAcDock(AeroScriptParser.AcDockContext ctx) {
		// RETURN TO BASE - nothing to check here;
		return null;
	}



	/*
	- Program and Mode checking
	- Programs and Modes(Executions) will return null
	- Format:
		program : (execution)+
 		execution : ARROW? ID LCURL statement* RCURL (ARROW ID)?
	*/
	@Override
	public String visitProgram(AeroScriptParser.ProgramContext ctx) {
		//check all modes
		for (var executionCtx: ctx.execution()) {
			visit(executionCtx);
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
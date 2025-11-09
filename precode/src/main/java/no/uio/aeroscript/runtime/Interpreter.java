package no.uio.aeroscript.runtime;

import no.uio.aeroscript.antlr.AeroScriptBaseVisitor;
import no.uio.aeroscript.antlr.AeroScriptParser;
import no.uio.aeroscript.antlr.AeroScriptParser.AcAscendContext;
import no.uio.aeroscript.antlr.AeroScriptParser.AcDescendContext;
import no.uio.aeroscript.antlr.AeroScriptParser.AcDockContext;
import no.uio.aeroscript.antlr.AeroScriptParser.AcMoveContext;
import no.uio.aeroscript.antlr.AeroScriptParser.AcTurnContext;
import no.uio.aeroscript.antlr.AeroScriptParser.ActionContext;
import no.uio.aeroscript.antlr.AeroScriptParser.ExecutionContext;
import no.uio.aeroscript.ast.expr.*;
import no.uio.aeroscript.ast.stmt.*;
import no.uio.aeroscript.type.*;

import java.util.*;

public class Interpreter extends AeroScriptBaseVisitor<Object> {
    private HashMap<Memory, Object> heap;
    private Stack<Statement> stack;
    private HashMap<String, List<Statement>> methodTable = new HashMap<>();
    private HashMap<String, String> childTable = new HashMap<>();

    private HashMap<String, Runnable> listeners = new HashMap<>();  // For REPL
    private Execution firstExecution = null;

    public Interpreter(HashMap<Memory, Object> heap, Stack<Statement> stack) {
        this.heap = heap;
        this.stack = stack;
    }

    public void setFirstExecution(Execution firstExecution) {
        this.firstExecution = firstExecution;
    }
    public HashMap<String, Runnable> getListeners() {
        return this.listeners;
    }
    public Execution getFirstExecution() {
        return firstExecution;
    }


    @Override
    public ArrayList<Execution> visitProgram(AeroScriptParser.ProgramContext ctx) {
        ArrayList<Execution> executionArrayList = new ArrayList<Execution>();

        /* Initializing lookup tables */
        HashMap<String, Execution> executionTable = new HashMap<>();
        HashMap<String, String> messages = new HashMap<>();
        HashMap<String, String> reactions = new HashMap<>();

        /* Storing in heap for access during execution */
        heap.put(Memory.EXECUTIONS, executionTable);
        heap.put(Memory.MESSAGES, messages);
        heap.put(Memory.REACTIONS, reactions);

        /* 1. Create all Execution objects */
        for (AeroScriptParser.ExecutionContext executionContext : ctx.execution()) {
            Execution execution = (Execution) visit(executionContext);    
            executionArrayList.add(execution);
            methodTable.put(execution.getName(), new ArrayList<>(execution.getStatements()));
            executionTable.put(execution.getName(), execution);  // new: Store in executionTable

            if (execution.getChild() != null) { //Store child relationships
                childTable.put(execution.getName(), execution.getChild());
            }
        }

        /* 2. Process Reactions */
        for (AeroScriptParser.ExecutionContext execCtx : ctx.execution()) {
            String modeName = execCtx.ID(0).getText();      
            for (AeroScriptParser.StatementContext stmtCtx : execCtx.statement()) {
                if (stmtCtx.reaction() != null) {
                    AeroScriptParser.ReactionContext reactionCtx = stmtCtx.reaction();
                    String targetMode = reactionCtx.ID().getText();                  
                    AeroScriptParser.EventContext eventCtx = reactionCtx.event(); // Determine event type
                    if (eventCtx.OBSTACLE() != null) {
                        reactions.put("obstacle", targetMode);
                        System.out.println("Registered reaction: obstacle -> " + targetMode);
                    } else if (eventCtx.LOW() != null && eventCtx.BATTERY() != null) {
                        // on low battery -> TargetMode
                        reactions.put("low battery", targetMode);
                        System.out.println("Registered reaction: low battery -> " + targetMode); 
                    } else if (eventCtx.MESSAGE() != null) {
                        // on message [messageName] -> TargetMode
                        String messageName = eventCtx.ID().getText();
                        messages.put(messageName, targetMode);
                        System.out.println("Registered message: " + messageName + " -> " + targetMode);
                        // Register listener for REPL
                        final String msg = messageName;
                        listeners.put(messageName, () -> {
                            if (firstExecution != null) {
                                firstExecution.receiveMessage(msg);
                            }
                        });
                    }
                }
            }
        }

        if (firstExecution != null) {
            firstExecution.execute();
        }

        return executionArrayList;
    }

    @Override
    public Object visitExecution(ExecutionContext ctx) {       
        String execution = ctx.ID(0).getText();
        boolean hasStartArw = false;
        if (ctx.ARROW().size() > 0) {
            int arrowPos = ctx.ARROW(0).getSymbol().getTokenIndex();
            int idPos = ctx.ID(0).getSymbol().getTokenIndex();
            hasStartArw = (arrowPos < idPos);  // Arrow before ID = start arrow
        }
        String nextEx = (ctx.ID(1) != null) ? ctx.ID(1).getText() : null;   
        ArrayList<Statement> statements = new ArrayList<>();
        for (AeroScriptParser.StatementContext statementContext: ctx.statement()) {
            // Modified for oblig3: Only add actions to statements (reactions are handled separately)
            if (statementContext.action() != null) {
                Statement s = (Statement) visit(statementContext);
                statements.add(s);
            }
        }   
        HashMap<String, Execution> nestedExecutions = new HashMap<>();  // Can be empty for now
        Execution newExecution = new Execution(
            execution,             
            statements,             
            nestedExecutions,     
            heap,             
            stack,              
            listeners,            
            hasStartArw            
        );   
        newExecution.setChild(nextEx); 
        if (hasStartArw) firstExecution = newExecution;
        return newExecution;
    }

    /* For Oblig3 */
    @Override
    public Object visitReaction(AeroScriptParser.ReactionContext ctx) {
        // Reactions are processed in visitProgram. This method doesn't need to do anything special
        return null;
    }

    @Override
    public Object visitAction(ActionContext ctx) {  
        Expression time = null;
        Expression speed = null;

        if (ctx.SECONDS() != null) {
            time = (Expression) visit(ctx.expression());
        } else if (ctx.SPEED() != null) {
            speed = (Expression) visit(ctx.expression());
        }

        if (ctx.acAscend() != null) {
            return buildAcAscend(ctx.acAscend(), time, speed);
        } else if (ctx.acDescend() != null) {
            return buildAcDescend(ctx.acDescend(), time, speed);
        } else if (ctx.acMove() != null) {
            return buildAcMove(ctx.acMove(), time, speed);
        } else if (ctx.acTurn() != null) {  
            return buildAcTurn(ctx.acTurn(), time, speed);
        } else {
            return buildAcDock(ctx.acDock(), time, speed);
        }
    }

    private AscendAction buildAcAscend(AcAscendContext context, Expression time, Expression speed) {
        Expression distance = (Expression) visit(context.expression());
        return new AscendAction(distance, time, speed);
    }
    private DescendAction buildAcDescend(AcDescendContext context, Expression time, Expression speed) {
        Expression distance = null;
        boolean toGround = false;
        if (context.GROUND() != null) {
            toGround = true;
        } else if (context.expression() != null) {  // Only visit if expression exists
            distance = (Expression) visit(context.expression());
        }
        return new DescendAction(distance, time, speed, toGround);
    }
    private MoveAction buildAcMove(AcMoveContext context, Expression time, Expression speed) {
        String moveType = "";
        Object distance;
        if (context.POINT() != null) {
            moveType = "Point";
            distance = visit(context.point());
        } else {
            moveType = "Number";
            distance = new NumberExpression(Float.parseFloat(context.NUMBER().getText()));
        }
        return new MoveAction(moveType, distance, time, speed);
    }
    private TurnAction buildAcTurn(AcTurnContext context, Expression time, Expression speed) {
        String direction = "left";
        Expression angle;
        if (context.RIGHT() != null) direction = "right";
        angle = (Expression) visit(context.expression());
        return new TurnAction(direction, angle, time, speed);
    }
    private DockAction buildAcDock(AcDockContext context, Expression time, Expression speed) {
        return new DockAction(time, speed);
    }

    @SuppressWarnings("unchecked")
    public Point getPosition() { return (Point) ((HashMap<String, Object>) heap.get(Memory.VARIABLES)).get("current position"); }
    @SuppressWarnings("unchecked")
    public float getBatteryLevel() { return (float) ((HashMap<String, Object>) heap.get(Memory.VARIABLES)).get("battery level"); }
    @SuppressWarnings("unchecked")
    public float getDistanceTravelled() { return (float) ((HashMap<String, Object>) heap.get(Memory.VARIABLES)).get("distance travelled"); }

    @Override
    public Object visitPoint(AeroScriptParser.PointContext ctx) {
        Expression xNode = (Expression) visit(ctx.expression(0));
        Expression yNode = (Expression) visit(ctx.expression(1));
        float x = Float.parseFloat(xNode.evaluate().toString());
        float y = Float.parseFloat(yNode.evaluate().toString());
        return new Point(x, y);
    }

    @Override
    public Object visitRange(AeroScriptParser.RangeContext ctx) {
        Expression startNode = (Expression) visit(ctx.expression(0));
        Expression endNode = (Expression) visit(ctx.expression(1));
        float start = Float.parseFloat(startNode.evaluate().toString());
        float end = Float.parseFloat(endNode.evaluate().toString());
        return new Range(start, end);
    }


    /* Expressions */
    public Expression visitExpression(AeroScriptParser.ExpressionContext ctx) {
        return switch (ctx) {
            case AeroScriptParser.PlusExpContext pex -> visitPlusExp(pex);
            case AeroScriptParser.MinusExpContext pex -> visitMinusExp(pex);
            case AeroScriptParser.TimesExpContext pex -> visitTimesExp(pex);
            case AeroScriptParser.NumExpContext pex -> visitNumExp(pex);
            case AeroScriptParser.NegExpContext pex -> visitNegExp(pex);
            case AeroScriptParser.ParentExpContext pex -> visitParentExp(pex);
            case AeroScriptParser.RangeExpContext pex -> visitRangeExp(pex);
            case AeroScriptParser.PointExpContext pex -> visitPointExp(pex);
            default -> new NumberExpression((float) 0) ;
        };
    }
    @Override
    public Expression visitPlusExp(AeroScriptParser.PlusExpContext ctx){
        Expression left = visitExpression(ctx.left);
        Expression right = visitExpression(ctx.right);
        return new OperationExpression("SUM",left, right);
    }
    @Override
    public Expression visitMinusExp(AeroScriptParser.MinusExpContext ctx){
        Expression left = visitExpression(ctx.left);
        Expression right = visitExpression(ctx.right);
        return new OperationExpression("SUB",left, right);
    }
    @Override
    public Expression visitTimesExp(AeroScriptParser.TimesExpContext ctx){
        Expression left = visitExpression(ctx.left);
        Expression right = visitExpression(ctx.right);
        return new OperationExpression("MUL",left, right);
    }
    @Override
    public Expression visitNumExp(AeroScriptParser.NumExpContext ctx){
        return new NumberExpression(Float.parseFloat(ctx.NUMBER().getText()));
    }
    @Override
    public Expression visitNegExp(AeroScriptParser.NegExpContext ctx){
        return new NegNumberExpression(visitExpression(ctx.expression())) ;
    }
    @Override
    public Expression visitParentExp(AeroScriptParser.ParentExpContext ctx){
        return visitExpression(ctx.expression());
    }
    @Override
    public Expression visitPointExp(AeroScriptParser.PointExpContext ctx){
        Expression left = visitExpression(ctx.point().left);
        Expression right = visitExpression(ctx.point().right);
        return new PointExpression(left, right);
    }
    @Override
    public Expression visitRangeExp(AeroScriptParser.RangeExpContext ctx){
        Expression left = visitExpression(ctx.range().left);
        Expression right = visitExpression(ctx.range().right);
        return new RangeExpression(left, right);
    }
}

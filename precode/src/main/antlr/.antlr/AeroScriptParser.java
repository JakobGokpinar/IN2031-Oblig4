// Generated from /Users/jakobgokpinar/Documents/Institutt for Informatikk/Semester-5/IN2031/Oblig3/precode/src/main/antlr/AeroScript.g4 by ANTLR 4.13.1

package no.uio.aeroscript.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class AeroScriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, COMMENT=2, LINE_COMMENT=3, LCURL=4, RCURL=5, LSQUARE=6, RSQUARE=7, 
		LPAREN=8, RPAREN=9, NEG=10, SEMI=11, COMMA=12, GREATER=13, ARROW=14, PLUS=15, 
		MINUS=16, TIMES=17, ASCEND=18, DESCEND=19, MOVE=20, TURN=21, RETURN=22, 
		TO=23, BY=24, AT=25, FOR=26, SPEED=27, SECONDS=28, BASE=29, GROUND=30, 
		LEFT=31, RIGHT=32, ON=33, OBSTACLE=34, LOW=35, BATTERY=36, MESSAGE=37, 
		RANDOM=38, POINT=39, ID=40, NUMBER=41;
	public static final int
		RULE_program = 0, RULE_execution = 1, RULE_statement = 2, RULE_action = 3, 
		RULE_acDock = 4, RULE_acMove = 5, RULE_acTurn = 6, RULE_acAscend = 7, 
		RULE_acDescend = 8, RULE_reaction = 9, RULE_event = 10, RULE_expression = 11, 
		RULE_point = 12, RULE_range = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "execution", "statement", "action", "acDock", "acMove", "acTurn", 
			"acAscend", "acDescend", "reaction", "event", "expression", "point", 
			"range"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'{'", "'}'", "'['", "']'", "'('", "')'", "'--'", 
			"';'", "','", "'>'", "'->'", "'+'", "'-'", "'*'", "'ascend'", "'descend'", 
			"'move'", "'turn'", "'return'", "'to'", "'by'", "'at'", "'for'", "'speed'", 
			"'seconds'", "'base'", "'ground'", "'left'", "'right'", "'on'", "'obstacle'", 
			"'low'", "'battery'", "'message'", "'random'", "'point'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENT", "LINE_COMMENT", "LCURL", "RCURL", "LSQUARE", "RSQUARE", 
			"LPAREN", "RPAREN", "NEG", "SEMI", "COMMA", "GREATER", "ARROW", "PLUS", 
			"MINUS", "TIMES", "ASCEND", "DESCEND", "MOVE", "TURN", "RETURN", "TO", 
			"BY", "AT", "FOR", "SPEED", "SECONDS", "BASE", "GROUND", "LEFT", "RIGHT", 
			"ON", "OBSTACLE", "LOW", "BATTERY", "MESSAGE", "RANDOM", "POINT", "ID", 
			"NUMBER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "AeroScript.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AeroScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public List<ExecutionContext> execution() {
			return getRuleContexts(ExecutionContext.class);
		}
		public ExecutionContext execution(int i) {
			return getRuleContext(ExecutionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(28);
				execution();
				}
				}
				setState(31); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ARROW || _la==ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExecutionContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(AeroScriptParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(AeroScriptParser.ID, i);
		}
		public TerminalNode LCURL() { return getToken(AeroScriptParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(AeroScriptParser.RCURL, 0); }
		public List<TerminalNode> ARROW() { return getTokens(AeroScriptParser.ARROW); }
		public TerminalNode ARROW(int i) {
			return getToken(AeroScriptParser.ARROW, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ExecutionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_execution; }
	}

	public final ExecutionContext execution() throws RecognitionException {
		ExecutionContext _localctx = new ExecutionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_execution);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ARROW) {
				{
				setState(33);
				match(ARROW);
				}
			}

			setState(36);
			match(ID);
			setState(37);
			match(LCURL);
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8598061056L) != 0)) {
				{
				{
				setState(38);
				statement();
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(44);
			match(RCURL);
			setState(47);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(45);
				match(ARROW);
				setState(46);
				match(ID);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public ReactionContext reaction() {
			return getRuleContext(ReactionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ASCEND:
			case DESCEND:
			case MOVE:
			case TURN:
			case RETURN:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				action();
				}
				break;
			case ON:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				reaction();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ActionContext extends ParserRuleContext {
		public AcDockContext acDock() {
			return getRuleContext(AcDockContext.class,0);
		}
		public AcMoveContext acMove() {
			return getRuleContext(AcMoveContext.class,0);
		}
		public AcTurnContext acTurn() {
			return getRuleContext(AcTurnContext.class,0);
		}
		public AcAscendContext acAscend() {
			return getRuleContext(AcAscendContext.class,0);
		}
		public AcDescendContext acDescend() {
			return getRuleContext(AcDescendContext.class,0);
		}
		public TerminalNode FOR() { return getToken(AeroScriptParser.FOR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SECONDS() { return getToken(AeroScriptParser.SECONDS, 0); }
		public TerminalNode AT() { return getToken(AeroScriptParser.AT, 0); }
		public TerminalNode SPEED() { return getToken(AeroScriptParser.SPEED, 0); }
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_action);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RETURN:
				{
				setState(53);
				acDock();
				}
				break;
			case MOVE:
				{
				setState(54);
				acMove();
				}
				break;
			case TURN:
				{
				setState(55);
				acTurn();
				}
				break;
			case ASCEND:
				{
				setState(56);
				acAscend();
				}
				break;
			case DESCEND:
				{
				setState(57);
				acDescend();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(67);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FOR:
				{
				setState(60);
				match(FOR);
				setState(61);
				expression(0);
				setState(62);
				match(SECONDS);
				}
				break;
			case AT:
				{
				setState(64);
				match(AT);
				setState(65);
				match(SPEED);
				setState(66);
				expression(0);
				}
				break;
			case RCURL:
			case ASCEND:
			case DESCEND:
			case MOVE:
			case TURN:
			case RETURN:
			case ON:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AcDockContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(AeroScriptParser.RETURN, 0); }
		public TerminalNode TO() { return getToken(AeroScriptParser.TO, 0); }
		public TerminalNode BASE() { return getToken(AeroScriptParser.BASE, 0); }
		public AcDockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acDock; }
	}

	public final AcDockContext acDock() throws RecognitionException {
		AcDockContext _localctx = new AcDockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_acDock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(RETURN);
			setState(70);
			match(TO);
			setState(71);
			match(BASE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AcMoveContext extends ParserRuleContext {
		public TerminalNode MOVE() { return getToken(AeroScriptParser.MOVE, 0); }
		public TerminalNode TO() { return getToken(AeroScriptParser.TO, 0); }
		public TerminalNode POINT() { return getToken(AeroScriptParser.POINT, 0); }
		public PointContext point() {
			return getRuleContext(PointContext.class,0);
		}
		public TerminalNode BY() { return getToken(AeroScriptParser.BY, 0); }
		public TerminalNode NUMBER() { return getToken(AeroScriptParser.NUMBER, 0); }
		public AcMoveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acMove; }
	}

	public final AcMoveContext acMove() throws RecognitionException {
		AcMoveContext _localctx = new AcMoveContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_acMove);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(MOVE);
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TO:
				{
				setState(74);
				match(TO);
				setState(75);
				match(POINT);
				setState(76);
				point();
				}
				break;
			case BY:
				{
				setState(77);
				match(BY);
				setState(78);
				match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AcTurnContext extends ParserRuleContext {
		public TerminalNode TURN() { return getToken(AeroScriptParser.TURN, 0); }
		public TerminalNode BY() { return getToken(AeroScriptParser.BY, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT() { return getToken(AeroScriptParser.RIGHT, 0); }
		public TerminalNode LEFT() { return getToken(AeroScriptParser.LEFT, 0); }
		public AcTurnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acTurn; }
	}

	public final AcTurnContext acTurn() throws RecognitionException {
		AcTurnContext _localctx = new AcTurnContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_acTurn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(TURN);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFT || _la==RIGHT) {
				{
				setState(82);
				_la = _input.LA(1);
				if ( !(_la==LEFT || _la==RIGHT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(85);
			match(BY);
			setState(86);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AcAscendContext extends ParserRuleContext {
		public TerminalNode ASCEND() { return getToken(AeroScriptParser.ASCEND, 0); }
		public TerminalNode BY() { return getToken(AeroScriptParser.BY, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AcAscendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acAscend; }
	}

	public final AcAscendContext acAscend() throws RecognitionException {
		AcAscendContext _localctx = new AcAscendContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_acAscend);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(ASCEND);
			setState(89);
			match(BY);
			setState(90);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AcDescendContext extends ParserRuleContext {
		public TerminalNode DESCEND() { return getToken(AeroScriptParser.DESCEND, 0); }
		public TerminalNode BY() { return getToken(AeroScriptParser.BY, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TO() { return getToken(AeroScriptParser.TO, 0); }
		public TerminalNode GROUND() { return getToken(AeroScriptParser.GROUND, 0); }
		public AcDescendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acDescend; }
	}

	public final AcDescendContext acDescend() throws RecognitionException {
		AcDescendContext _localctx = new AcDescendContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_acDescend);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(DESCEND);
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BY:
				{
				setState(93);
				match(BY);
				setState(94);
				expression(0);
				}
				break;
			case TO:
				{
				setState(95);
				match(TO);
				setState(96);
				match(GROUND);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReactionContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(AeroScriptParser.ON, 0); }
		public EventContext event() {
			return getRuleContext(EventContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(AeroScriptParser.ARROW, 0); }
		public TerminalNode ID() { return getToken(AeroScriptParser.ID, 0); }
		public ReactionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reaction; }
	}

	public final ReactionContext reaction() throws RecognitionException {
		ReactionContext _localctx = new ReactionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_reaction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(ON);
			setState(100);
			event();
			setState(101);
			match(ARROW);
			setState(102);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EventContext extends ParserRuleContext {
		public TerminalNode OBSTACLE() { return getToken(AeroScriptParser.OBSTACLE, 0); }
		public TerminalNode LOW() { return getToken(AeroScriptParser.LOW, 0); }
		public TerminalNode BATTERY() { return getToken(AeroScriptParser.BATTERY, 0); }
		public TerminalNode MESSAGE() { return getToken(AeroScriptParser.MESSAGE, 0); }
		public TerminalNode LSQUARE() { return getToken(AeroScriptParser.LSQUARE, 0); }
		public TerminalNode ID() { return getToken(AeroScriptParser.ID, 0); }
		public TerminalNode RSQUARE() { return getToken(AeroScriptParser.RSQUARE, 0); }
		public EventContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_event; }
	}

	public final EventContext event() throws RecognitionException {
		EventContext _localctx = new EventContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_event);
		try {
			setState(111);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OBSTACLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				match(OBSTACLE);
				}
				break;
			case LOW:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				match(LOW);
				setState(106);
				match(BATTERY);
				}
				break;
			case MESSAGE:
				enterOuterAlt(_localctx, 3);
				{
				setState(107);
				match(MESSAGE);
				setState(108);
				match(LSQUARE);
				setState(109);
				match(ID);
				setState(110);
				match(RSQUARE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PlusExpContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode PLUS() { return getToken(AeroScriptParser.PLUS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public PlusExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TimesExpContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode TIMES() { return getToken(AeroScriptParser.TIMES, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TimesExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NegExpContext extends ExpressionContext {
		public TerminalNode NEG() { return getToken(AeroScriptParser.NEG, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NegExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumExpContext extends ExpressionContext {
		public TerminalNode NUMBER() { return getToken(AeroScriptParser.NUMBER, 0); }
		public NumExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParentExpContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(AeroScriptParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(AeroScriptParser.RPAREN, 0); }
		public ParentExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RangeExpContext extends ExpressionContext {
		public TerminalNode RANDOM() { return getToken(AeroScriptParser.RANDOM, 0); }
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public RangeExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MinusExpContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode MINUS() { return getToken(AeroScriptParser.MINUS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MinusExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PointExpContext extends ExpressionContext {
		public TerminalNode POINT() { return getToken(AeroScriptParser.POINT, 0); }
		public PointContext point() {
			return getRuleContext(PointContext.class,0);
		}
		public PointExpContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NEG:
				{
				_localctx = new NegExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(114);
				match(NEG);
				setState(115);
				expression(8);
				}
				break;
			case NUMBER:
				{
				_localctx = new NumExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(116);
				match(NUMBER);
				}
				break;
			case RANDOM:
				{
				_localctx = new RangeExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(117);
				match(RANDOM);
				setState(118);
				range();
				}
				break;
			case POINT:
				{
				_localctx = new PointExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(119);
				match(POINT);
				setState(120);
				point();
				}
				break;
			case LPAREN:
				{
				_localctx = new ParentExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				match(LPAREN);
				setState(122);
				expression(0);
				setState(123);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(138);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(136);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new TimesExpContext(new ExpressionContext(_parentctx, _parentState));
						((TimesExpContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(127);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(128);
						match(TIMES);
						setState(129);
						((TimesExpContext)_localctx).right = expression(8);
						}
						break;
					case 2:
						{
						_localctx = new PlusExpContext(new ExpressionContext(_parentctx, _parentState));
						((PlusExpContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(130);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(131);
						match(PLUS);
						setState(132);
						((PlusExpContext)_localctx).right = expression(7);
						}
						break;
					case 3:
						{
						_localctx = new MinusExpContext(new ExpressionContext(_parentctx, _parentState));
						((MinusExpContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(133);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(134);
						match(MINUS);
						setState(135);
						((MinusExpContext)_localctx).right = expression(6);
						}
						break;
					}
					} 
				}
				setState(140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PointContext extends ParserRuleContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode LPAREN() { return getToken(AeroScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(AeroScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(AeroScriptParser.RPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public PointContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_point; }
	}

	public final PointContext point() throws RecognitionException {
		PointContext _localctx = new PointContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_point);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(LPAREN);
			setState(142);
			((PointContext)_localctx).left = expression(0);
			setState(143);
			match(COMMA);
			setState(144);
			((PointContext)_localctx).right = expression(0);
			setState(145);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RangeContext extends ParserRuleContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode LSQUARE() { return getToken(AeroScriptParser.LSQUARE, 0); }
		public TerminalNode COMMA() { return getToken(AeroScriptParser.COMMA, 0); }
		public TerminalNode RSQUARE() { return getToken(AeroScriptParser.RSQUARE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_range; }
	}

	public final RangeContext range() throws RecognitionException {
		RangeContext _localctx = new RangeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_range);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(LSQUARE);
			setState(148);
			((RangeContext)_localctx).left = expression(0);
			setState(149);
			match(COMMA);
			setState(150);
			((RangeContext)_localctx).right = expression(0);
			setState(151);
			match(RSQUARE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 11:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001)\u009a\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0004\u0000\u001e\b\u0000\u000b"+
		"\u0000\f\u0000\u001f\u0001\u0001\u0003\u0001#\b\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u0001(\b\u0001\n\u0001\f\u0001+\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0003\u00010\b\u0001\u0001\u0002\u0001"+
		"\u0002\u0003\u00024\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003;\b\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003D\b"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005P\b"+
		"\u0005\u0001\u0006\u0001\u0006\u0003\u0006T\b\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bb\b\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0003\np\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0003\u000b~\b\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0005\u000b\u0089\b\u000b\n\u000b\f\u000b\u008c\t\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0000\u0001\u0016\u000e\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u0000\u0001\u0001\u0000"+
		"\u001f \u00a2\u0000\u001d\u0001\u0000\u0000\u0000\u0002\"\u0001\u0000"+
		"\u0000\u0000\u00043\u0001\u0000\u0000\u0000\u0006:\u0001\u0000\u0000\u0000"+
		"\bE\u0001\u0000\u0000\u0000\nI\u0001\u0000\u0000\u0000\fQ\u0001\u0000"+
		"\u0000\u0000\u000eX\u0001\u0000\u0000\u0000\u0010\\\u0001\u0000\u0000"+
		"\u0000\u0012c\u0001\u0000\u0000\u0000\u0014o\u0001\u0000\u0000\u0000\u0016"+
		"}\u0001\u0000\u0000\u0000\u0018\u008d\u0001\u0000\u0000\u0000\u001a\u0093"+
		"\u0001\u0000\u0000\u0000\u001c\u001e\u0003\u0002\u0001\u0000\u001d\u001c"+
		"\u0001\u0000\u0000\u0000\u001e\u001f\u0001\u0000\u0000\u0000\u001f\u001d"+
		"\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000\u0000 \u0001\u0001\u0000"+
		"\u0000\u0000!#\u0005\u000e\u0000\u0000\"!\u0001\u0000\u0000\u0000\"#\u0001"+
		"\u0000\u0000\u0000#$\u0001\u0000\u0000\u0000$%\u0005(\u0000\u0000%)\u0005"+
		"\u0004\u0000\u0000&(\u0003\u0004\u0002\u0000\'&\u0001\u0000\u0000\u0000"+
		"(+\u0001\u0000\u0000\u0000)\'\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000"+
		"\u0000*,\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000,/\u0005\u0005"+
		"\u0000\u0000-.\u0005\u000e\u0000\u0000.0\u0005(\u0000\u0000/-\u0001\u0000"+
		"\u0000\u0000/0\u0001\u0000\u0000\u00000\u0003\u0001\u0000\u0000\u0000"+
		"14\u0003\u0006\u0003\u000024\u0003\u0012\t\u000031\u0001\u0000\u0000\u0000"+
		"32\u0001\u0000\u0000\u00004\u0005\u0001\u0000\u0000\u00005;\u0003\b\u0004"+
		"\u00006;\u0003\n\u0005\u00007;\u0003\f\u0006\u00008;\u0003\u000e\u0007"+
		"\u00009;\u0003\u0010\b\u0000:5\u0001\u0000\u0000\u0000:6\u0001\u0000\u0000"+
		"\u0000:7\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000:9\u0001\u0000"+
		"\u0000\u0000;C\u0001\u0000\u0000\u0000<=\u0005\u001a\u0000\u0000=>\u0003"+
		"\u0016\u000b\u0000>?\u0005\u001c\u0000\u0000?D\u0001\u0000\u0000\u0000"+
		"@A\u0005\u0019\u0000\u0000AB\u0005\u001b\u0000\u0000BD\u0003\u0016\u000b"+
		"\u0000C<\u0001\u0000\u0000\u0000C@\u0001\u0000\u0000\u0000CD\u0001\u0000"+
		"\u0000\u0000D\u0007\u0001\u0000\u0000\u0000EF\u0005\u0016\u0000\u0000"+
		"FG\u0005\u0017\u0000\u0000GH\u0005\u001d\u0000\u0000H\t\u0001\u0000\u0000"+
		"\u0000IO\u0005\u0014\u0000\u0000JK\u0005\u0017\u0000\u0000KL\u0005\'\u0000"+
		"\u0000LP\u0003\u0018\f\u0000MN\u0005\u0018\u0000\u0000NP\u0005)\u0000"+
		"\u0000OJ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000\u0000P\u000b\u0001"+
		"\u0000\u0000\u0000QS\u0005\u0015\u0000\u0000RT\u0007\u0000\u0000\u0000"+
		"SR\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000"+
		"\u0000UV\u0005\u0018\u0000\u0000VW\u0003\u0016\u000b\u0000W\r\u0001\u0000"+
		"\u0000\u0000XY\u0005\u0012\u0000\u0000YZ\u0005\u0018\u0000\u0000Z[\u0003"+
		"\u0016\u000b\u0000[\u000f\u0001\u0000\u0000\u0000\\a\u0005\u0013\u0000"+
		"\u0000]^\u0005\u0018\u0000\u0000^b\u0003\u0016\u000b\u0000_`\u0005\u0017"+
		"\u0000\u0000`b\u0005\u001e\u0000\u0000a]\u0001\u0000\u0000\u0000a_\u0001"+
		"\u0000\u0000\u0000b\u0011\u0001\u0000\u0000\u0000cd\u0005!\u0000\u0000"+
		"de\u0003\u0014\n\u0000ef\u0005\u000e\u0000\u0000fg\u0005(\u0000\u0000"+
		"g\u0013\u0001\u0000\u0000\u0000hp\u0005\"\u0000\u0000ij\u0005#\u0000\u0000"+
		"jp\u0005$\u0000\u0000kl\u0005%\u0000\u0000lm\u0005\u0006\u0000\u0000m"+
		"n\u0005(\u0000\u0000np\u0005\u0007\u0000\u0000oh\u0001\u0000\u0000\u0000"+
		"oi\u0001\u0000\u0000\u0000ok\u0001\u0000\u0000\u0000p\u0015\u0001\u0000"+
		"\u0000\u0000qr\u0006\u000b\uffff\uffff\u0000rs\u0005\n\u0000\u0000s~\u0003"+
		"\u0016\u000b\bt~\u0005)\u0000\u0000uv\u0005&\u0000\u0000v~\u0003\u001a"+
		"\r\u0000wx\u0005\'\u0000\u0000x~\u0003\u0018\f\u0000yz\u0005\b\u0000\u0000"+
		"z{\u0003\u0016\u000b\u0000{|\u0005\t\u0000\u0000|~\u0001\u0000\u0000\u0000"+
		"}q\u0001\u0000\u0000\u0000}t\u0001\u0000\u0000\u0000}u\u0001\u0000\u0000"+
		"\u0000}w\u0001\u0000\u0000\u0000}y\u0001\u0000\u0000\u0000~\u008a\u0001"+
		"\u0000\u0000\u0000\u007f\u0080\n\u0007\u0000\u0000\u0080\u0081\u0005\u0011"+
		"\u0000\u0000\u0081\u0089\u0003\u0016\u000b\b\u0082\u0083\n\u0006\u0000"+
		"\u0000\u0083\u0084\u0005\u000f\u0000\u0000\u0084\u0089\u0003\u0016\u000b"+
		"\u0007\u0085\u0086\n\u0005\u0000\u0000\u0086\u0087\u0005\u0010\u0000\u0000"+
		"\u0087\u0089\u0003\u0016\u000b\u0006\u0088\u007f\u0001\u0000\u0000\u0000"+
		"\u0088\u0082\u0001\u0000\u0000\u0000\u0088\u0085\u0001\u0000\u0000\u0000"+
		"\u0089\u008c\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000"+
		"\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u0017\u0001\u0000\u0000\u0000"+
		"\u008c\u008a\u0001\u0000\u0000\u0000\u008d\u008e\u0005\b\u0000\u0000\u008e"+
		"\u008f\u0003\u0016\u000b\u0000\u008f\u0090\u0005\f\u0000\u0000\u0090\u0091"+
		"\u0003\u0016\u000b\u0000\u0091\u0092\u0005\t\u0000\u0000\u0092\u0019\u0001"+
		"\u0000\u0000\u0000\u0093\u0094\u0005\u0006\u0000\u0000\u0094\u0095\u0003"+
		"\u0016\u000b\u0000\u0095\u0096\u0005\f\u0000\u0000\u0096\u0097\u0003\u0016"+
		"\u000b\u0000\u0097\u0098\u0005\u0007\u0000\u0000\u0098\u001b\u0001\u0000"+
		"\u0000\u0000\u000e\u001f\")/3:COSao}\u0088\u008a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
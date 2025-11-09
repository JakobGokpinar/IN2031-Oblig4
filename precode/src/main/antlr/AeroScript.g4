grammar AeroScript;

@header {
package no.uio.aeroscript.antlr;
}

// Whitespace and comments added
WS           : [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT      : '/*' .*? '*/' -> channel(HIDDEN) ;
LINE_COMMENT : '//' ~[\r\n]* -> channel(HIDDEN) ;

LCURL   : '{';
RCURL   : '}';
LSQUARE : '[';
RSQUARE : ']';
LPAREN  : '(';
RPAREN  : ')';

NEG     : '--';
SEMI    : ';';
COMMA   : ',';
GREATER : '>';
ARROW   : '->';

PLUS    : '+';
MINUS   : '-';
TIMES   : '*';

// Oblig2
ASCEND  : 'ascend';
DESCEND : 'descend';
MOVE    : 'move';
TURN    : 'turn';
RETURN  : 'return';

TO      : 'to';
BY      : 'by';
AT      : 'at';
FOR     : 'for';
SPEED   : 'speed';
SECONDS : 'seconds';
BASE    : 'base';
GROUND  : 'ground';
LEFT    : 'left';
RIGHT   : 'right';

// Oblig3: Event keywords
ON       : 'on';
OBSTACLE : 'obstacle';
LOW      : 'low';
BATTERY  : 'battery';
MESSAGE  : 'message';

RANDOM  : 'random';
POINT   : 'point';

// Keywords
ID: [a-zA-Z_]+; // Allow underscore in ID
NUMBER: [0-9]+('.'[0-9]+)?;

// Entry point
program : (execution)+ ; //this is why ProgramContext ctx only has access to execution() 

 execution : ARROW? ID LCURL statement* RCURL (ARROW ID)?; //this is why executionContext has access to ID, statemetn, ARROW

 statement: action | reaction;

 action: (acDock | acMove | acTurn | acAscend | acDescend)
        (FOR expression SECONDS | AT SPEED expression)?;

 acDock: RETURN TO BASE;
 acMove: MOVE (TO POINT point | BY NUMBER);
 acTurn: TURN (RIGHT | LEFT)? BY expression;
 acAscend: ASCEND BY expression;
 acDescend: DESCEND (BY expression | TO GROUND);

 reaction: ON event ARROW ID;

 event: OBSTACLE | LOW BATTERY | MESSAGE LSQUARE ID RSQUARE;

expression : NEG expression #NegExp
            | left = expression TIMES right = expression #TimesExp
            | left = expression PLUS right = expression #PlusExp
            | left = expression MINUS right = expression #MinusExp
            | NUMBER #NumExp
            | RANDOM range #RangeExp
            | POINT point #PointExp
            | LPAREN expression RPAREN #ParentExp
            ;

point : LPAREN left = expression COMMA right = expression RPAREN ;
range : LSQUARE left = expression COMMA right = expression RSQUARE ;
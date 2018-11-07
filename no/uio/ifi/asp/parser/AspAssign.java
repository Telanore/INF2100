package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAssign extends AspStmt{

    AspName name;
    AspSubs subscr;
    AspExpr expr;

    
    public AspAssign(int n){
        super(n);
    }
    
    
    static AspAssign parse(Scanner s){
        enterParser(" assign ");

        AspAssign aa = new AspAssign(s.curLineNum());
        
        if(s.curToken().kind != nameToken){
            AspSyntax.parserError("Assignment without name", s.curLineNum());
        }
        aa.name = AspName.parse(s);
        
        while(s.curToken().kind == leftBracketToken){
            aa.subscr = AspSubs.parse(s);
        }

        skip(s, equalToken);
        aa.expr = AspExpr.parse(s);
    
        while(s.curToken().kind == newLineToken){
            skip(s, newLineToken);
        }

        leaveParser(" assign ");
        return aa;
    }

    @Override
    void prettyPrint(){
        name.prettyPrint();
        if(subscr != null) subscr.prettyPrint();
        Main.log.prettyWrite(" = ");
        expr.prettyPrint();
    }


    RuntimeValue eval(RuntimeScope curScope){
        return null;
    }
        
}

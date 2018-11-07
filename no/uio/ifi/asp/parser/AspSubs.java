package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSubs extends AspPrimSuf{

    protected AspExpr expr;

    public AspSubs(int n){
        super(n);
    }

    static AspSubs parse(Scanner s){
        enterParser(" subscrpt ");
        AspSubs as = new AspSubs(s.curLineNum());
        
        skip(s, s.curToken().kind);
        as.expr = AspExpr.parse(s);
        skip(s, rightBracketToken);

        leaveParser(" subscrpt ");
        return as;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("[");
        expr.prettyPrint();
        Main.log.prettyWrite("]");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return expr.eval(curScope);
    }
}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspReturn extends AspStmt{

    protected AspExpr expr;


    public AspReturn(int n){
        super(n);
    }


    static AspReturn parse(Scanner s){
        enterParser(" return ");
        AspReturn ar = new AspReturn(s.curLineNum());

        skip(s, returnToken);
        ar.expr = AspExpr.parse(s);

        while(s.curToken().kind == newLineToken){
            skip(s, newLineToken);
        }

        leaveParser(" return ");
        return ar;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("return ");
        expr.prettyPrint();

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}

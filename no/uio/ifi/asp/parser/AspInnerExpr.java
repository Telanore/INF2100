package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspInnerExpr extends AspAtom{

    protected AspExpr expr;

    public AspInnerExpr(int n){
        super(n);
    }

    static AspInnerExpr parse(Scanner s){
        enterParser(" inner expr ");
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());
       
        skip(s, s.curToken().kind);
        aie.expr = AspExpr.parse(s);
        skip(s, rightParToken);

        leaveParser(" inner expr ");
        return aie;
    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("(");
        expr.prettyPrint();
        Main.log.prettyWrite(")");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

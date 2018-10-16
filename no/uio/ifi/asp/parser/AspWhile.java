package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspWhile extends AspStmt{

    protected AspExpr expr;
    protected AspSuite suite;


    public AspWhile(int n){
        super(n);
    }


    static AspWhile parse(Scanner s){
        enterParser(" while ");
        AspWhile aw = new AspWhile(s.curLineNum());

        skip(s, whileToken);
        aw.expr = AspExpr.parse(s);
        skip(s, colonToken);
        aw.suite = AspSuite.parse(s);

        leaveParser(" while ");
        return aw;
    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("while ");
        expr.prettyPrint();
        Main.log.prettyWrite(" :");
        suite.prettyPrint();

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

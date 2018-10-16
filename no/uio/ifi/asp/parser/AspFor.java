package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFor extends AspStmt{
    
    protected AspName name;
    protected AspExpr expr;
    protected AspSuite suite;

    public AspFor(int n){
        super(n);
    }
    
    
    static AspFor parse(Scanner s){
        enterParser(" for ");
        AspFor af = new AspFor(s.curLineNum());

        skip(s, forToken);

        af.name = AspName.parse(s);

        skip(s, inToken);
        af.expr = AspExpr.parse(s);

        skip(s, colonToken);
        af.suite = AspSuite.parse(s);

        leaveParser(" for ");
        return af;
    }
    

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("for ");
        name.prettyPrint();
        Main.log.prettyWrite(" in ");
        expr.prettyPrint();
        Main.log.prettyWrite(":");
        Main.log.finish();
        suite.prettyPrint();

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspCompOp extends AspSyntax{

    
    protected TokenKind op;

    public AspCompOp(int n){
        super(n);
    }


    static AspCompOp parse(Scanner s){
        enterParser(" comp op ");
        AspCompOp aco = new AspCompOp(s.curLineNum());

        aco.op = s.curToken().kind;
        skip(s, aco.op);

        leaveParser(" comp op ");
        return aco;
    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" "+op+" ");

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

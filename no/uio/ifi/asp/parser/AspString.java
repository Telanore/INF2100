package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspString extends AspAtom{

    protected Token t;

    public AspString(int n){
        super(n);
    }

    static AspString parse(Scanner s){
        enterParser(" string ");
        AspString as= new AspString(s.curLineNum());

        as.t = s.curToken();
        skip(s, s.curToken().kind);
        
        leaveParser(" string ");
        return as;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("\""+t.stringLit+"\"");

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}


package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspInt extends AspAtom{

    protected Token t;

    public AspInt(int n){
        super(n);
    }

    static AspInt parse(Scanner s){
        enterParser(" integer ");
        AspInt ai = new AspInt(s.curLineNum());

        ai.t = s.curToken();
        skip(s, integerToken);
        
        leaveParser(" integer ");
        return ai;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(String.valueOf(t.integerLit));

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

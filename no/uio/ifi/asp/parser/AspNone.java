package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspNone extends AspAtom{

    protected Token t;

    public AspNone(int n){
        super(n);
    }

    static AspNone parse(Scanner s){
        enterParser(" none ");
        AspNone an = new AspNone(s.curLineNum());
        
        test(s, noneToken);

        leaveParser(" none ");
        return an;
    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(" None ");

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeNoneValue();
    }
}

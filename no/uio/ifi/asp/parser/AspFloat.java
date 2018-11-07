package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFloat extends AspAtom{

    protected Token t;

    public AspFloat(int n){
        super(n);
    }

    static AspFloat parse(Scanner s){
        enterParser(" float ");
        AspFloat af = new AspFloat(s.curLineNum());

        af.t = s.curToken();
        skip(s, floatToken);

        leaveParser(" float ");
        return af;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite(String.valueOf(" "+t.floatLit+" "));

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return new RuntimeFloatValue(t.floatLit);
    }
}

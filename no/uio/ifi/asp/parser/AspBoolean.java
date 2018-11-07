package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspBoolean extends AspAtom{

    protected TokenKind tk;

    public AspBoolean(int n){
        super(n);
    }

    static AspBoolean parse(Scanner s){
        enterParser(" boolean ");
        AspBoolean ab = new AspBoolean(s.curLineNum());
        
        ab.tk = s.curToken().kind;
        skip(s, s.curToken().kind);

        leaveParser(" boolean ");
        return ab;
    }


    @Override
    public void prettyPrint() {
        if(tk == trueToken) Main.log.prettyWrite("True");
        else Main.log.prettyWrite("False");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        if(tk == trueToken) return new RuntimeBoolValue(true);
        else return new RuntimeBoolValue(false);
    }
}

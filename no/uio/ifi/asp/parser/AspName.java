package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspName extends AspAtom{

    protected Token name;

    public AspName(int n){
        super(n);
    }


    static AspName parse(Scanner s){
        enterParser(" name ");
        AspName an = new AspName(s.curLineNum());
        
        an.name = s.curToken();
        skip(s, nameToken);
        
        leaveParser(" name ");
        return an;
    }

    
    @Override
    void prettyPrint() {
        Main.log.prettyWrite(name.name);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope){
        return null;
    }
}

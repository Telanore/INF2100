package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFacPre extends AspSyntax{

    protected TokenKind prefix;

    public AspFacPre(int n){
        super(n);
    }


    static AspFacPre parse(Scanner s){
        
        enterParser(" factor prefix ");
        AspFacPre afp = new AspFacPre(s.curLineNum());

        afp.prefix = s.curToken().kind;
        skip(s, s.curToken().kind);

        leaveParser(" factor prefix ");
        return afp;

    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("FACPRE");
        if(prefix == plusToken) Main.log.prettyWrite(" + ");
        else if(prefix == minusToken) Main.log.prettyWrite(" - ");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspNotTest extends AspSyntax{

    protected AspComp ac;
    protected boolean hasNot = false;

    public AspNotTest(int n){
        super(n);
    }


    static AspNotTest parse(Scanner s){
        enterParser(" not test ");
        AspNotTest ant = new AspNotTest(s.curLineNum());

       if(s.curToken().kind == notToken){
            ant.hasNot = true;
            skip(s, notToken);
       }

        ant.ac = AspComp.parse(s);

        leaveParser(" not test ");
        return ant;

    }

    @Override
    void prettyPrint() {
        if(hasNot) Main.log.prettyWrite(" not ");
        ac.prettyPrint();
    }
    

    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
        RuntimeValue v = ac.eval(curScope);
        if(hasNot){
            v = v.evalNot(this);
        }
        
        return v;
    }


}

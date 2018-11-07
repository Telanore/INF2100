package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspPass extends AspStmt{

    public AspPass(int n){
        super(n);
    }
    

    static AspPass parse(Scanner s){
        enterParser(" pass "); 
        AspPass ap = new AspPass(s.curLineNum());

        skip(s, passToken);
        
        while(s.curToken().kind == newLineToken){
            skip(s, newLineToken);
        }

        leaveParser(" pass ");
        return ap;
    }

    void prettyPrint(){
        Main.log.prettyWrite("pass ");
    }


    RuntimeValue eval(RuntimeScope curScope){
        return null;
    }
    
}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspPrimSuf extends AspSyntax{

    public AspPrimSuf(int n){
        super(n);
    }
    
    static AspPrimSuf parse(Scanner s){
        enterParser(" primary suffix ");
        AspPrimSuf aps = null;

        if(s.curToken().kind == TokenKind.leftParToken) aps = AspArg.parse(s);
        else if(s.curToken().kind == TokenKind.leftBracketToken) aps = AspSubs.parse(s);
        else parserError(s.curToken()+" is not a valid primary suffix", s.curLineNum());

    
        leaveParser(" primary suffix ");
        return aps;
    }
    
    
    abstract void prettyPrint();
    abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspAtom extends AspPrim{

    public AspAtom(int n){
        super(n);
    }

    static AspAtom parse(Scanner s){
        enterParser(" atom ");
        AspAtom aa = null;
        
        if(s.curToken().kind == nameToken) aa = AspName.parse(s);
        else if(s.curToken().kind == integerToken) aa = AspInt.parse(s);
        else if(s.curToken().kind == floatToken) aa = AspFloat.parse(s);
        else if(s.curToken().kind == stringToken) aa = AspString.parse(s);
        else if(s.isBoolean()) aa = AspBoolean.parse(s);
        else if(s.curToken().kind == noneToken) aa = AspNone.parse(s);
        else if(s.curToken().kind == leftParToken) aa = AspInnerExpr.parse(s);
        else if(s.curToken().kind == leftBracketToken) aa = AspList.parse(s);
        else if(s.curToken().kind == leftBraceToken) aa = AspDict.parse(s);
        else{
            System.out.println("Current token: "+s.curToken().kind);
            parserError("Invalid atom: "+s.curToken().kind, s.curLineNum());
        }

        leaveParser(" atom ");
        return aa;
    }


    abstract void prettyPrint();
    abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}

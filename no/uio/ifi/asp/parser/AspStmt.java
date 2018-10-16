package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspStmt extends AspSyntax{

    public AspStmt(int n){
        super(n);
    }
    
    static AspStmt parse(Scanner s){
        enterParser(" statement ");
        
        TokenKind curTok = s.curToken().kind;
        AspStmt as;

        if(curTok == TokenKind.forToken){
            as = AspFor.parse(s);
        }else if(curTok == TokenKind.passToken){
            as = AspPass.parse(s);
        }else if(curTok == TokenKind.ifToken){
            as = AspIf.parse(s);
        }else if(curTok == TokenKind.returnToken){
            as = AspReturn.parse(s);
        }else if(curTok == TokenKind.whileToken){
            as = AspWhile.parse(s);
        }else if(curTok == defToken){
            as = AspFuncDef.parse(s);
        }else if(curTok == TokenKind.nameToken){
            if(s.anyEqualToken()){
                as = AspAssign.parse(s);
            }else{
                as = AspExprStmt.parse(s);
            }
        }else{
            AspSyntax.parserError("No matching statement found for "+curTok, s.curLineNum());
            return null;
        }

        leaveParser(" statement ");
        return as;

    }
    
    abstract void prettyPrint();
    abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;

}


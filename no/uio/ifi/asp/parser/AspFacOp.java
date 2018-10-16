package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFacOp extends AspSyntax{

    protected Token op;

    public AspFacOp(int n){
        super(n);
    }


    static AspFacOp parse(Scanner s){
        enterParser(" factor op ");
        AspFacOp afo = new AspFacOp(s.curLineNum());

        afo.op = s.curToken();
        skip(s, s.curToken().kind);

        leaveParser(" factor op ");
        return afo;
    }


    @Override
    public void prettyPrint() {
        
        if(op.kind == astToken) Main.log.prettyWrite(" * ");
        else if(op.kind == slashToken) Main.log.prettyWrite(" / ");
        else if(op.kind == percentToken) Main.log.prettyWrite(" % ");
        else if(op.kind == doubleSlashToken) Main.log.prettyWrite(" // ");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}

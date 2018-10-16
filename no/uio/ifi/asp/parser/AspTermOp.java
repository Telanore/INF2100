package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspTermOp extends AspSyntax{

    protected TokenKind op;


    public AspTermOp(int n){
        super(n);    
    }


    static AspTermOp parse(Scanner s){
        enterParser(" term op ");
        AspTermOp ato = new AspTermOp(s.curLineNum());

        ato.op = s.curToken().kind;
        skip(s, ato.op);

        leaveParser(" term op ");
        return ato;
    }


    @Override
    public void prettyPrint() {
        if(op == plusToken) Main.log.prettyWrite(" + ");
        else if(op == minusToken) Main.log.prettyWrite(" - ");

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}

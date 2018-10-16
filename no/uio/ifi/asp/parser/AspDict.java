package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspDict extends AspAtom{

    protected ArrayList<AspString> names = new ArrayList<>();
    protected ArrayList<AspExpr> exprs = new ArrayList<>();


    public AspDict(int n){
        super(n);
    }

    static AspDict parse(Scanner s){
        enterParser(" dict ");
        AspDict ad = new AspDict(s.curLineNum());
        
        skip(s, s.curToken().kind);

        if(s.curToken().kind != rightBraceToken){
            while(true){
                if(s.curToken().kind != stringToken) parserError("Expected string literal, but found "+s.curToken().kind, s.curLineNum());
                ad.names.add(AspString.parse(s));
                skip(s, colonToken);
                ad.exprs.add(AspExpr.parse(s));
                if(s.curToken().kind == rightBraceToken) break;
                skip(s, commaToken);
            }

        }
        

        leaveParser(" dict ");
        return ad;
    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("{");
        for(int i = 0; i < names.size()-1; i++){
            names.get(i).prettyPrint();
            Main.log.prettyWrite(": ");
            exprs.get(i).prettyPrint();
            Main.log.prettyWrite(", ");
        }
        names.get(names.size()-1).prettyPrint();
        Main.log.prettyWrite(": ");
        exprs.get(exprs.size()-1).prettyPrint();
        Main.log.prettyWrite("}");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

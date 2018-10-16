package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspList extends AspAtom{

    protected ArrayList<AspExpr> exprs = new ArrayList<>();


    public AspList(int n){
        super(n);
    }

    static AspList parse(Scanner s){
        enterParser(" list ");
        AspList al = new AspList(s.curLineNum());
        
        skip(s, leftBracketToken);
        
        if(s.curToken().kind != rightBracketToken){
            while(true){
                al.exprs.add(AspExpr.parse(s));

                if(s.curToken().kind != commaToken) break;
                skip(s, commaToken);
            }
        }
        skip(s, rightBracketToken);

        leaveParser(" list ");
        return al;
    }


    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("[");
        for(int i = 0; i < exprs.size()-1; i++){
            exprs.get(i).prettyPrint();
            Main.log.prettyWrite(", ");
        }
        exprs.get(exprs.size()-1).prettyPrint();
        Main.log.prettyWrite("]");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

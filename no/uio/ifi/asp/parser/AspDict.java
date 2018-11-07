package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDict extends AspAtom{

    protected ArrayList<AspString> names = new ArrayList<>();
    protected ArrayList<AspExpr> exprs = new ArrayList<>();


    public AspDict(int n){
        super(n);
    }

    static AspDict parse(Scanner s){
        enterParser(" dict ");
        AspDict ad = new AspDict(s.curLineNum());
        
        skip(s, leftBraceToken);

        if(s.curToken().kind != rightBraceToken){
            while(true){
                if(s.curToken().kind != stringToken){ 
                    parserError("Expected string literal, but found "+s.curToken().kind, s.curLineNum());
                }
                ad.names.add(AspString.parse(s));
                skip(s, colonToken);
                ad.exprs.add(AspExpr.parse(s));
                if(s.curToken().kind == rightBraceToken) break;
                skip(s, commaToken);
            }

        }
        skip(s, rightBraceToken);

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
        HashMap<RuntimeValue, RuntimeValue> rtVals = new HashMap<>();
        for(int i = 0; i < names.size(); i++){
            rtVals.put(names.get(i).eval(curScope), exprs.get(i).eval(curScope));
        }
        return new RuntimeDictValue(rtVals);
    }
}

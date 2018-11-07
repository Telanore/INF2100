package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspArg extends AspPrimSuf{

    protected ArrayList<AspExpr> exprs = new ArrayList<>();

    public AspArg(int n){
        super(n);
    }


    static AspArg parse(Scanner s){
        enterParser(" argument ");
        AspArg aa = new AspArg(s.curLineNum());

        skip(s, leftParToken);
        while(true){
            if(s.curToken().kind == rightParToken) break;
            aa.exprs.add(AspExpr.parse(s));
            if(s.curToken().kind != TokenKind.commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightParToken);

        leaveParser(" argument ");
        return aa;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("(");
        for(int i = 0; i < exprs.size()-1; i++){
            exprs.get(i).prettyPrint();
            Main.log.prettyWrite(", ");
        }
        if(exprs.size() > 0) exprs.get(exprs.size()-1).prettyPrint();
        Main.log.prettyWrite(")");
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> vals = new ArrayList<>();
        for(AspExpr ae: exprs) vals.add(ae.eval(curScope));
        return new RuntimeListValue(vals);
    }

}

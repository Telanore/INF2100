package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax{
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
        super(n);
    }


    public static AspExpr parse(Scanner s) {
        enterParser(" expr ");

        //-- Must be changed in part 2:
        AspExpr ae = new AspExpr(s.curLineNum());
        ae.andTests.add(AspAndTest.parse(s));

        while(true){
            if(s.curToken().kind != orToken) break;
            skip(s, orToken);
            ae.andTests.add(AspAndTest.parse(s));
        }
        
        leaveParser(" expr ");
        return ae;
    }


    @Override
    public void prettyPrint() {
        int nPrinted = 0;

        for(AspAndTest aat: andTests){
            if(nPrinted > 0) Main.log.prettyWrite(" or ");
            aat.prettyPrint();
            ++nPrinted;
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = andTests.get(0).eval(curScope);
        for(int i = 1; i < andTests.size(); i++){
            RuntimeValue w = andTests.get(i).eval(curScope);
            if(v.getBoolValue("|| operand", this) || w.getBoolValue("|| operand", this)){
                v = w;
            }else{
                return w;
            }
        }
        return v;
    }
}

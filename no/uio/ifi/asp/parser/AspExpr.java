package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax{
    //-- Must be changed in part 2:
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

        for(AspAndTest aat: andTests){
            aat.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

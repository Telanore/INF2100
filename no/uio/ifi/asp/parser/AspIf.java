package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIf extends AspStmt{

    protected ArrayList<AspExpr> exprs = new ArrayList<>();
    protected ArrayList<AspSuite> ifSuite = new ArrayList<>();
    protected AspSuite elseSuite;
    
    public AspIf(int n){
        super(n);
    }


    static AspIf parse(Scanner s){
        enterParser(" if ");
        AspIf ai = new AspIf(s.curLineNum());

        skip(s, ifToken);
        System.out.println("IF");
        while(true){
            ai.exprs.add(AspExpr.parse(s));
            skip(s, colonToken);
            ai.ifSuite.add(AspSuite.parse(s));
            System.out.println("IF POST SUITE");
            if(s.curToken().kind != TokenKind.elifToken) break;
            System.out.println("ELIF");
            skip(s, elifToken);
        }
        if(s.curToken().kind == elseToken){
            System.out.println("ELSE");
            skip(s, elseToken);
            skip(s, colonToken);
            ai.elseSuite = AspSuite.parse(s);
        }
        System.out.println("IF DONE "+s.curToken());
        leaveParser(" if ");
        return ai;
    }



    @Override
    public void prettyPrint() {
        Main.log.prettyWrite("if ");
        exprs.get(0).prettyPrint();
        Main.log.prettyWrite(":");
        ifSuite.get(0).prettyPrint();
        Main.log.finish();

        if(exprs.size() > 1){
            for(int i = 1; i < exprs.size(); i++){
                Main.log.prettyWrite("elif ");
                exprs.get(i).prettyPrint();
                Main.log.prettyWrite(":");
                ifSuite.get(i).prettyPrint();
            }
            Main.log.finish();

        }
        
        if(elseSuite != null){
            Main.log.prettyWrite("else:");
            elseSuite.prettyPrint();
        }
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

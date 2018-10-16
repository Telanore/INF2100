package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFuncDef extends AspStmt{

    protected AspName name;
    protected ArrayList<AspName> params = new ArrayList<>();
    protected AspSuite suite;
    

    public AspFuncDef(int n){
        super(n);
    }


    static AspFuncDef parse(Scanner s){
        enterParser(" func def ");
        AspFuncDef afd = new AspFuncDef(s.curLineNum());
        
        skip(s, defToken);
        afd.name = AspName.parse(s);

        skip(s, leftParToken);
        while(true){
            if(s.curToken().kind != nameToken) break;
            afd.params.add(AspName.parse(s));
            if(s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        skip(s, colonToken);

        afd.suite = AspSuite.parse(s);

        leaveParser(" func def ");
        return afd;
    }


    @Override
    public void prettyPrint() {
        //whitespacing for readability
        Main.log.finish();
        Main.log.prettyWrite(" ");
        Main.log.finish();
    
        Main.log.prettyWrite("def ");
        name.prettyPrint();
        Main.log.prettyWrite("(");

        for(AspName an: params){
            an.prettyPrint();
        }
        Main.log.prettyWrite("):");
        
        suite.prettyPrint();

        Main.log.prettyWrite(" ");
        Main.log.finish();

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}

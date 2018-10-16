package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
        super(n);
    }


    public static AspProgram parse(Scanner s) {
        enterParser(" program ");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            //-- Must be changed in part 2:
            while(s.curToken().kind == newLineToken){
                skip(s, newLineToken);
            }
            while(s.curToken().kind == dedentToken){
                skip(s, dedentToken);
            }
            
            if(s.curToken().kind == eofToken) break;
            else ap.stmts.add(AspStmt.parse(s));
        }

        leaveParser(" program ");
        return ap;
    }


    @Override
    public void prettyPrint(){
        int nPrinted = 0;

        for(AspStmt as: stmts){
            as.prettyPrint();
            Main.log.finish();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}

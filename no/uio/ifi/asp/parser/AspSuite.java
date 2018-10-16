package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSuite extends AspSyntax{

    protected int indents;
    protected ArrayList<AspStmt> stmts = new ArrayList<>();

    public AspSuite(int n){
        super(n);
    }


    static AspSuite parse(Scanner s){
        enterParser(" suite ");
        AspSuite as = new AspSuite(s.curLineNum());
        
        skip(s, newLineToken);
        as.indents = s.getNumIndents();

        skip(s, indentToken);

        while(as.indents > s.getNumIndents() || s.curToken().kind != eofToken){
            while(s.curToken().kind == newLineToken){
                skip(s, newLineToken);
            }
            if(s.curToken().kind == indentToken){
                skip(s, indentToken);
            }
            if(s.curToken().kind == dedentToken){
                skip(s, dedentToken);
                break;
            }
            
            if(s.curToken().kind == eofToken) break;

            else{
                as.stmts.add(AspStmt.parse(s));
            }
        }
        leaveParser(" suite ");
        return as;
    }

    
    @Override
    public void prettyPrint() {
        Main.log.finish();
        Main.log.prettyIndent();
        for(int i = 0; i < stmts.size(); i++){
            stmts.get(i).prettyPrint();
            Main.log.finish();

        }
        Main.log.prettyDedent();

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}

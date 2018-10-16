package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspComp extends AspSyntax{

    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOp> ops = new ArrayList<>();


    public AspComp(int n){
        super(n);
    }

    
    static AspComp parse(Scanner s){
        enterParser(" comparison ");

        AspComp ac = new AspComp(s.curLineNum());
        
        while(true){
            ac.terms.add(AspTerm.parse(s));
            if(!s.isCompOp()) break;
            ac.ops.add(AspCompOp.parse(s));
        }


        leaveParser(" comparison ");
        return ac;
    }

    @Override
    void prettyPrint() {

        for(int i = 0; i < terms.size(); i++){
            terms.get(i).prettyPrint();
            if(i < ops.size()) ops.get(i).prettyPrint();
        }

    }
    
    RuntimeValue eval(RuntimeScope curScope){
        return null;
    }
}

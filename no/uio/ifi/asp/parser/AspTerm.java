package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTerm extends AspSyntax{

    ArrayList<AspFactor> facs = new ArrayList<>();
    ArrayList<AspTermOp> ops = new ArrayList<>();

    
    public AspTerm(int n){
        super(n);
    }

    static AspTerm parse(Scanner s){
        enterParser(" term ");

        AspTerm at = new AspTerm(s.curLineNum());
        
        while(true){
            at.facs.add(AspFactor.parse(s));
            if(!s.isTermOp()) break;
            at.ops.add(AspTermOp.parse(s));
        }


        leaveParser(" term ");
        return at;
    }

    @Override
    void prettyPrint() {
        
        for(int i = 0; i < facs.size(); i++){
            facs.get(i).prettyPrint();
            if(i < ops.size()) ops.get(i).prettyPrint();
            else break;
        }
    
    }
    
    
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
        RuntimeValue v =  facs.get(0).eval(curScope);
        for(int i = 1; i < facs.size(); i++){
            TokenKind k = ops.get(i-1).op;
            switch (k) {
                case plusToken:
                    v = v.evalAdd(facs.get(i).eval(curScope), this);
                    break;
                case minusToken:
                    v = v.evalSubtract(facs.get(i).eval(curScope), this);
                    break;
                default:
                    Main.panic("Illegal term op: "+k);
            }
        }
        return v;
    }
}

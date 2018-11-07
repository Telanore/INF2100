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
    
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
        RuntimeValue v = terms.get(0).eval(curScope);
        RuntimeValue copy = v;
        for(int i = 1; i < terms.size(); i++){
            TokenKind k = ops.get(i-1).op;
            switch (k) {
                case doubleEqualToken:
                    v = copy.evalEqual(terms.get(i).eval(curScope), this);
                    if(!v.getBoolValue("== op", this)) return v;
                    break;

                case notEqualToken:
                    v = copy.evalNotEqual(terms.get(i).eval(curScope), this);
                    if(!v.getBoolValue("!= op", this)) return v;
                    break;

                case lessToken:
                    v = copy.evalLess(terms.get(i).eval(curScope), this);
                    if(!v.getBoolValue("< op", this)) return v;
                    break;

                case lessEqualToken:
                    v = copy.evalLessEqual(terms.get(i).eval(curScope), this);
                    if(!v.getBoolValue("<= op", this)) return v;
                    break;
                    
                case greaterToken:
                    v = copy.evalGreater(terms.get(i).eval(curScope), this);
                    if(!v.getBoolValue("> op", this)) return v;
                    break;

                case greaterEqualToken:
                    v = copy.evalGreaterEqual(terms.get(i).eval(curScope), this);
                    if(!v.getBoolValue(">= op", this)) return v;
                    break;

                default:
                    Main.panic("Illegal comp op: "+ k);
            }
            copy = terms.get(i).eval(curScope);
        }
        return v;
    }
}

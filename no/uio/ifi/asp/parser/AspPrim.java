package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspPrim extends AspSyntax{

    protected AspAtom a;
    protected ArrayList<AspPrimSuf> primSufs = new ArrayList<>();
    
    public AspPrim(int n){
        super(n);
    }

    
    static AspPrim parse(Scanner s){
        enterParser(" primary ");
        AspPrim ap = new AspPrim(s.curLineNum());

        ap.a = AspAtom.parse(s);
        
        while(s.curToken().kind == leftBracketToken || s.curToken().kind == leftParToken){
            ap.primSufs.add(AspPrimSuf.parse(s));
        }

        leaveParser(" primary ");
        return ap;
    }


    @Override
    void prettyPrint() {
        a.prettyPrint();
        for(AspPrimSuf aps: primSufs){
            aps.prettyPrint();
        }

    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = a.eval(curScope);
        if(primSufs.size() > 0){
            for(int i = 0; i < primSufs.size(); i++){
                RuntimeValue w = primSufs.get(i).eval(curScope);
                if(primSufs.get(i) instanceof AspSubs){
                    v = v.evalSubscription(w, this);
                }
            }


        }
        return v;
    }

}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactor extends AspSyntax{
   
    ArrayList<AspPrim> prims = new ArrayList<>();
    ArrayList<AspFacOp> facOps = new ArrayList<>();
    ArrayList<AspFacPre> facPres = new ArrayList<>();
   
    public AspFactor(int n){
        super(n);
    }

    static AspFactor parse(Scanner s){
        enterParser(" factor ");
        AspFactor af = new AspFactor(s.curLineNum());


        while(true){
            if(s.isFactorPrefix()){
                af.facPres.add(AspFacPre.parse(s));
            }else af.facPres.add(null);
            af.prims.add(AspPrim.parse(s));
            if(!s.isFactorOp()) break;
            af.facOps.add(AspFacOp.parse(s));
            
        }


        leaveParser(" factor ");
        return af;
    }


    @Override
    void prettyPrint() {

        for(int i = 0; i < prims.size(); i++){
            if(facPres.get(i) != null) facPres.get(i).prettyPrint();
            prims.get(i).prettyPrint();
            if(i < facOps.size()) facOps.get(i).prettyPrint();
        }
        
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = prims.get(0).eval(curScope);
        RuntimeValue w;
        TokenKind pre = null;
        if(facPres.get(0) != null){
            pre = facPres.get(0).prefix;
            switch (pre){
                case plusToken: v = v.evalPositive(this);
                case minusToken: v = v.evalNegate(this);
            }
        }

        for(int i = 1; i < prims.size(); i++){
            TokenKind k = facOps.get(i-1).op.kind;
            if(facPres.get(i) != null){
                pre = facPres.get(i).prefix;
            }
            switch (k) {
                case astToken:
                    if(pre == plusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalMultiply(w.evalPositive(this), this);
                    }else if(pre == minusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalMultiply(w.evalNegate(this),this);
                    }else{
                        v = v.evalMultiply(prims.get(i).eval(curScope), this);
                    }
                    break;
                case slashToken:
                    if(pre == plusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalDivide(w.evalPositive(this), this);
                    }else if(pre == minusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalDivide(w.evalNegate(this),this);
                    }else{
                        v = v.evalDivide(prims.get(i).eval(curScope), this);
                    }
                    break;
                case percentToken:
                    if(pre == plusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalModulo(w.evalPositive(this), this);
                    }else if(pre == minusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalModulo(w.evalNegate(this),this);
                    }else{
                        v = v.evalModulo(prims.get(i).eval(curScope), this);
                    }
                    break;
                case doubleSlashToken:
                    if(pre == plusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalIntDivide(w.evalPositive(this), this);
                    }else if(pre == minusToken){
                        w = prims.get(i).eval(curScope);
                        v = v.evalIntDivide(w.evalNegate(this),this);
                    }else{
                        v = v.evalIntDivide(prims.get(i).eval(curScope), this);
                    }
                    break;
                default:
                    Main.panic("Illegal term op: "+k);
            }
        }
        return v;
    }

}

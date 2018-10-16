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

        if(s.isFactorPrefix()){
            af.facPres.add(AspFacPre.parse(s));
        }

        while(true){
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
            if(i < facPres.size()) facPres.get(i).prettyPrint();
            prims.get(i).prettyPrint();
            if(i < facOps.size()) facOps.get(i).prettyPrint();
        }
        
    }


    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}

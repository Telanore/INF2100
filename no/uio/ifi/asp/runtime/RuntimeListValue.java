package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.*;

import java.util.ArrayList;


public class RuntimeListValue extends RuntimeValue {

    ArrayList<RuntimeValue> values = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> values){
        this.values = values;
    }

    @Override
    public String typeName(){
        return "list";
    }

    @Override
    public String toString(){
        String ret;
        if(values.size() > 0){
            ret = "["+values.get(0);
            for(int i = 1; i < values.size(); i++) ret += ", "+values.get(i);
            ret += "]";
        }else{
            ret = "[]";
        }
        return ret;
    }

    @Override
    public String showInfo(){
        return toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(values.size() > 0) return true;
        return false;
    }

    @Override
    public ArrayList<RuntimeValue> getListValue(String what, AspSyntax where){
        return values;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        ArrayList<RuntimeValue> combined = new ArrayList<>(values);
        combined.addAll(v.getListValue("+ op", where));
        return new RuntimeListValue(combined);
    }

    //First check instanceof v, then check if same object, then check if same size
    //of list values, then checks if toStrings of objects are equal, then checks
    //if they are same type
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeListValue){
            ArrayList<RuntimeValue> vVals = v.getListValue("== op", where);
            
            if(v == this) return new RuntimeBoolValue(true);
            else if(values.size() == vVals.size()){
                for(int i = 0; i < values.size(); i++){
                    RuntimeValue x = values.get(i);
                    RuntimeValue y = vVals.get(i);

                    if(!x.toString().equals(y.toString()) || 
                        !x.getClass().getName().equals(y.getClass().getName())){
                        return new RuntimeBoolValue(false);
                    }
                }
            }
        }else if(v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);
    }
    
    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            for(int i = 0; i < v.getIntValue("* op", where)-1; i++){
                values.addAll(values);
            }
            return new RuntimeListValue(values);
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return values.get((int)v.getIntValue("subscription", where));
        }
        runtimeError("Type error for subscription.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long)values.size());
    }


}

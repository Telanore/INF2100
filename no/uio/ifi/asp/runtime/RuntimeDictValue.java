package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.*;

import java.util.ArrayList;
import java.util.HashMap;


public class RuntimeDictValue extends RuntimeValue {

    protected HashMap<String, RuntimeValue> values = new HashMap<>();

    public RuntimeDictValue(HashMap<String, RuntimeValue> values){
        this.values = values;
    }

    @Override
    public String toString(){
        String ret = "{";
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<RuntimeValue> vals = new ArrayList<>();
        for(String key: values.keySet()) keys.add(key);
        for(String val: values.keySet()) vals.add(values.get(val)); 

        
        for(int i = 0; i < keys.size()-1; i++){
            ret += "\""+keys.get(i)+"\": "+vals.get(i)+", ";
        }

        ret += "\""+keys.get(keys.size()-1)+"\": "+vals.get(keys.size()-1)+"}";
        return ret;
    }

    @Override
    public HashMap<String, RuntimeValue> getDictValue(String what, AspSyntax where){
        return values;
    }

    @Override
    public String showInfo(){
        return toString();
    }

    @Override
    public String typeName(){
        return "dict";
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeDictValue){
            HashMap<String, RuntimeValue> ret = new HashMap<>(values);
            ret.putAll(v.getDictValue("+ op", where));
            return new RuntimeDictValue(ret);
        }
        runtimeError("Type error for +.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            if(values.get(v.toString()) == null)
                runtimeError("Dictionary key not found.", where);
            else return values.get(v.toString());
        }
        if(v instanceof RuntimeFloatValue){
            if(values.get(v.toString()) == null)
                runtimeError("Dictionary key not found.", where);
            else return values.get(v.toString());
        }
        if(v instanceof RuntimeStringValue){
            if(values.get(v.toString()) == null)
                runtimeError("Dictionary key not found.", where);
            else return values.get(v.toString());
        }
        runtimeError("Type error for dictionary key.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v == this) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long)values.size());
    }


}

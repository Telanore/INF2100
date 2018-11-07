package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.*;

import java.util.ArrayList;
import java.util.HashMap;


public class RuntimeDictValue extends RuntimeValue {

    protected HashMap<RuntimeValue, RuntimeValue> values = new HashMap<>();

    public RuntimeDictValue(HashMap<RuntimeValue, RuntimeValue> values){
        this.values = values;
    }

    @Override
    public String toString(){
        String ret = "{";
        for(RuntimeValue key: values.keySet()){
            ret += "\""+key.toString()+"\": "+values.get(key)+" ";
        }
        ret += "}";
        return ret;
    }

    @Override
    public HashMap<RuntimeValue, RuntimeValue> getDictValue(String what, AspSyntax where){
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
            HashMap<RuntimeValue, RuntimeValue> ret = new HashMap<>(values);
            ret.putAll(v.getDictValue("+ op", where));
            return new RuntimeDictValue(ret);
        }
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v == this) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long)values.size());
    }


}

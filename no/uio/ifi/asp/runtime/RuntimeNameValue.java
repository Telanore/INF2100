package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeNameValue extends RuntimeValue {

    String value;

    public RuntimeNameValue(String value){
        this.value = value;
    }

    public String toString(){
        return value;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(value.equals("")) return false;
        return true;
    }

    @Override
    public String getStringValue(String what, AspSyntax where){
        return toString();
    }

    @Override
    public String typeName(){
        return "name";
    }

    @Override
    public String showInfo(){
        return toString();
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(value.equals(v));
        }
        runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long)value.length());
    }

}

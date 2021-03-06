package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {

    String value;

    public RuntimeStringValue(String value){
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
        return "string";
    }

    @Override
    public String showInfo(){
        if(value.indexOf("\'") >= 0){
            return "'"+toString()+"'";
        }else{
            return '"'+toString()+'"';
        }
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeStringValue(value + v);
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeStringValue(value+v.getIntValue("+ op", where));
        }else if(v instanceof RuntimeFloatValue){
            return new RuntimeStringValue(value+v.getFloatValue("+ op", where));
        }
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        String ret = value;
        if(v instanceof RuntimeIntValue){
            long x = v.getIntValue("* op", where);
            for(long i = 0; i < x-1; i++) ret += value;
            return new RuntimeStringValue(ret);
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(value.equals(v.getStringValue("== op", where)));
        }else if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }
        return new RuntimeBoolValue(false);    
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            String s = v.getStringValue("<= op", where);
            int x = value.compareTo(s);
            if(x < 0) return new RuntimeBoolValue(true);
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            String s = v.getStringValue("<= op", where);
            int x = value.compareTo(s);
            if(x <= 0) return new RuntimeBoolValue(true);
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            String s = v.getStringValue("<= op", where);
            int x = value.compareTo(s);
            if(x > 0) return new RuntimeBoolValue(true);
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            String s = v.getStringValue("<= op", where);
            int x = value.compareTo(s);
            if(x >= 0) return new RuntimeBoolValue(true);
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            int index = (int)v.getIntValue("subscription", where);
            return new RuntimeStringValue(Character.toString(value.charAt(index)));
        }
        runtimeError("Type error for subscription.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long)value.length());
    }

}

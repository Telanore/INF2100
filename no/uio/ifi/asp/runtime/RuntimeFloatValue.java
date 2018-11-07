package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {

    private double value;

    public RuntimeFloatValue(double value){
        this.value = value;
    }

    @Override
    protected String typeName(){
        return "float";
    }

    @Override
    public String toString(){
        return ""+value;
    }

    @Override
    public String showInfo(){
        return toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where){
        if(value == 0.0) return false;
        return true;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where){
        return value;
    }
    
    @Override
    public RuntimeValue evalNegate(AspSyntax where){
        return new RuntimeFloatValue(-value);
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where){
        return new RuntimeFloatValue(+value);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(value + v.getFloatValue("+ op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(value + v.getIntValue("+ op", where));
        }
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(value - v.getFloatValue("- op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(value - v.getIntValue("- op", where));
        }
        runtimeError("Type error for -.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(value * v.getFloatValue("* op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(value * v.getIntValue("* op", where));
        }
        runtimeError("Type error for *.", where);
        return null;
    }
    @Override

    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(value / v.getFloatValue("/ op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(value / v.getIntValue("/ op", where));
        }
        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue((long) value / v.getFloatValue("// op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(value / v.getIntValue("// op", where));
        }
        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(value % v.getFloatValue("% op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(value % v.getIntValue("% op", where));
        }
        runtimeError("Type error for %.", where);
        return null;
    }
    
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(value == v.getFloatValue("== op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(value == v.getIntValue("== op", where));
        }
        runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(value != v.getFloatValue("!= op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(value != v.getIntValue("!= op", where));
        }
        runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(value <= v.getFloatValue("<= op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(value <= v.getIntValue("<= op", where));
        }
        runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(value < v.getFloatValue("< op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(value < v.getIntValue("< op", where));
        }
        runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(value >= v.getFloatValue(">= op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(value >= v.getIntValue(">= op", where));
        }
        runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(value > v.getFloatValue("> op", where));
        }else if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(value > v.getIntValue("> op", where));
        }
        runtimeError("Type error for >.", where);
        return null;
    }



}

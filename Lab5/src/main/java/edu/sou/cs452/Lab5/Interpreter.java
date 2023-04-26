package edu.sou.cs452.Lab5;

/* 
 * Your abstract interpreter will simply return the sign of the expression as an over-approximation of the possible values of the expression. 
 * For example, if the interpreter determines that an expression is either positive or negative it will return TOP. 
 * If the interpreter determines that an expression is definitely positive it will return POSITIVE. 
 * If the interpreter determines that an expression is definitely negative it will return NEGATIVE.
*/
import static edu.sou.cs452.Lab5.TokenType.*;
abstract class Interpreter implements SExpr.Visitor<Object> {
    void interpret(SExpr expression) { 
        try {
          Object value = evaluate(expression);
          System.out.println(stringify(value));
        } 
        catch (RuntimeError error) { Lox.runtimeError(error); }
    }
    /* 
     * a
    */
    private String stringify(Object object) {
        if ( object == null ) return "nil";
    
        if (object instanceof Double) {
          String text = object.toString();
          if (text.endsWith(".0")) { text = text.substring(0, text.length() - 2); }
          return text;
        }
        return object.toString();
    }
    /* 
     * This function is a helper which simply sends back the expression
    */
    private Object evaluate(SExpr expr) { return expr.accept(this); }
    
    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }
    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;

        throw new RuntimeError(operator, "Operands must be numbers.");
    }
    @Override
    public Object visitBinaryExpr(SExpr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right); 
        switch (expr.operator.type) {
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return (double)left - (double)right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) { return (double)left + (double)right; } 
                if (left instanceof String && right instanceof String) { return (String)left + (String)right; }
                throw new RuntimeError(expr.operator, "Operands must be two numbers or two strings.");
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double)left / (double)right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double)left * (double)right;
        }
        // Unreachable.
        return null;
    }
}

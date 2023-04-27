package edu.sou.cs452.Lab5;

/* 
 * Your abstract interpreter will simply return the sign of the expression as an over-approximation of the possible values of the expression. 
 * For example, if the interpreter determines that an expression is either positive or negative it will return TOP. 
 * If the interpreter determines that an expression is definitely positive it will return POSITIVE. 
 * If the interpreter determines that an expression is definitely negative it will return NEGATIVE.
*/
import static edu.sou.cs452.Lab5.AbstractValue.*;
class Interpreter implements Expr.Visitor<Object> {
    void interpret(Expr expression) { 
        try {
          Object value = evaluate(expression);
          System.out.println(stringify(value));
        } 
        catch (RuntimeError error) { Lox.runtimeError(error); }
    }
    /** 
     * .....
     * @param object is a Object type
     * @return Returns a string
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
    /** 
     * This function is a helper which simply sends back the expression
     * @param expr 
     * @return expr.accecpt(this)
    */
    private Object evaluate(Expr expr) { return expr.accept(this); }
    /** 
     * @param operator Is a Token type
     * @param operand is a Object type
     * @return None
    */
    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }
    /** 
     * @param Expr.Binary 
     * @return null if it is not reachable 
    */
    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Operands must be numbers.");
    }
    /** 
     * @param Expr.Binary 
     * @return null if it is not reachable 
    */
    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right); 
        switch (expr.operator.type) {
            case BANG_EQUAL: return !isEqual(left, right);
            case EQUAL_EQUAL: return isEqual(left, right);
            case GREATER: 
                checkNumberOperands(expr.operator, left, right);
                return (double)left > (double)right;
            case GREATER_EQUAL: 
                checkNumberOperands(expr.operator, left, right);
                return (double)left >= (double)right;
            case LESS: 
                checkNumberOperands(expr.operator, left, right);
                return (double)left < (double)right;
            case LESS_EQUAL: 
                checkNumberOperands(expr.operator, left, right);
                return (double)left <= (double)right;
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
        return null;
    }
    /** 
     * @param Expr.Unary 
     * @return null if it is not reachable
    */
    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);
  
        switch (expr.operator.type) {
            case BANG:
            return !isTruthy(right);
            case MINUS:
            return -(double)right;
        }
        return null;
    }
    /** 
     * @param object is a Object Type
     * @return True or False
    */
    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }
    /** 
     * @param a is a Object type
     * @param b is a Object type
     * @return a.equals(b)
    */
    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
        return a.equals(b);
    }
    /** 
     * @param expr  
     * @return evaluate(expr.expression)
    */
    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) { return evaluate(expr.expression); }
    /** 
     * @param expr  
     * @return evaluate(expr.expression)
    */
    @Override
    public Object visitLiteralExpr(Expr.Literal expr) { return expr.value; }

}

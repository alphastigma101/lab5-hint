package edu.sou.cs452.Lab5;
class Interpreter implements Expr.Visitor<Object> {
    /** 
     * ...
     * @param expression is Expr type
     * @return None
    */
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
        if (object == null) return "nil";
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
                checkNumberOperands(expr.operator, left, right);
                return (double)left - (double)right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) { return (double)left + (double)right; } 
                if (left instanceof String && right instanceof String) { return (String)left + (String)right; }
                break;
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double)left / (double)right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double)left * (double)right;
            default:
                break;
        }
        return null;
    }
    /** 
     * @param object is a Object Type
     * @return return false if object is null otherwise, cast object into a boolean and return true
    */
    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }
    /** 
     * @param expr is a Expr.Grouping type 
     * @return evaluate(expr.expression)
    */
    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
    
        return a.equals(b);
    }
    /** 
     * @param expr is a Expr.Grouping type 
     * @return evaluate(expr.expression)
    */
    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }
    /** 
     * @param expr is a Expr.Grouping type 
     * @return evaluate(expr.expression)
    */
    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Operands must be numbers.");
    }
    /** 
     * @param expr is a Expr.Grouping type 
     * @return evaluate(expr.expression)
    */
    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) { return evaluate(expr.expression); }
    /** 
     * @param expr is a Expr.Literal type  
     * @return evaluate(expr.expression)
    */
    @Override
    public Object visitLiteralExpr(Expr.Literal expr) { return expr.value; }
}

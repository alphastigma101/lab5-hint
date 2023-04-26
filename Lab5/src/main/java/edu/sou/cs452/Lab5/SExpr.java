package edu.sou.cs452.Lab5;


abstract class SExpr implements LoxIdentifer<String> {
    /* 
     * A
    */
    interface Visitor<R> {
        R visitNumber(Number expr);
        R visitPair(Pair expr);
        R visitGroupingExpr(Grouping expr);
        R visitBinaryExpr(Binary expr);
    } 
    /* 
     * A
    */ 
    static class Number extends SExpr {
        final Integer value;
        Number(int value) { this.value = value; }
        @Override
        public <R> R accept(Visitor<R> visitor) { return visitor.visitNumber(this); }
    
        @Override
        public String id() { return Integer.toString(this.value);  }
    }

    /* 
     * A
    */
    static class Pair extends SExpr {
        Pair(SExpr expression, SExpr right) { 
        this.right = right;
        this.left = expression;
        }
        public SExpr left;
        public SExpr right;

        @Override
        public <R> R accept(Visitor<R> visitor) { return visitor.visitPair(this); }

        @Override
        public String id() {
            String LeftID = this.left.id();
            String RightID = this.right.id();
        return LeftID  + "" + RightID;
        }
    }
    /* 
     * A
    */
    public abstract <R> R accept(Visitor<R> visitor);
}


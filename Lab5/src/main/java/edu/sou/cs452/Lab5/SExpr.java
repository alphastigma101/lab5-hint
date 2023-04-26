package edu.sou.cs452.Lab5;


abstract class SExpr implements LoxIdentifer<String> {
  interface Visitor<R> {
    R visitNumber(Number expr);
    R visitPair(Pair expr);
  }  
  static class Number extends SExpr {
    final Integer value;
    Number(int value) { this.value = value; }
    @Override
    public <R> R accept(Visitor<R> visitor) { return visitor.visitNumber(this); }
    
    @Override
    public String id() { return Integer.toString(this.value);  }
  }

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
      // On line 42 and 43 recrusive calls id() function so you avoid breaking the Abstraction Syntax Tree
      String LeftID = this.left.id();
      String RightID = this.right.id();
      return LeftID  + "" + RightID;
    }
  }
  public abstract <R> R accept(Visitor<R> visitor);
}


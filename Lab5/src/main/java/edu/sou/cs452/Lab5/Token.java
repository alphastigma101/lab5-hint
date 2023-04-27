package edu.sou.cs452.Lab5;

class Token {
    /** 
     * @param Expr.Binary 
     * @return null if it is not reachable 
    */
    final TokenType type;
    final String lexeme;
    final AbstractValue literal;
    final int line; 
    /** 
     * @param Expr.Binary 
     * @return null if it is not reachable 
    */
    Token(TokenType type, String lexeme, AbstractValue literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    /** 
     * @param Expr.Binary 
     * @return null if it is not reachable 
    */
    public String toString() { return type + " " + lexeme + " " + literal; } 
}
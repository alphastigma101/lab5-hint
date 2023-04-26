package edu.sou.cs452.Lab5;

class Token {
    /* 
     * 
    */
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line; 
    /* 
     * A
    */
    Token(TokenType type, String lexeme, LiteralValue literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    /* 
     * A
    */
    public String toString() {
        return type + " " + lexeme + " " + literal;
    } 
}
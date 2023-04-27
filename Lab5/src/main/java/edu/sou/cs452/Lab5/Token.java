package edu.sou.cs452.Lab5;

class Token {
    final TokenType type;
    final String lexeme;
    final AbstractValue literal;
    final int line; 
    /** 
     * @param type is a TokenType 
     * @param lexeme is a String type. String type functions like a wrapper
     * @param literal is a AbstractValue type 
     * @param line is a integer type
     * @return None
    */
    Token(TokenType type, String lexeme, AbstractValue literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    /** 
     * @param None
     * @return type + " " + lexeme + " " + literal
    */
    public String toString() { return type + " " + lexeme + " " + literal; } 
}
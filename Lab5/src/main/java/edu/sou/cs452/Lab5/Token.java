package edu.sou.cs452.Lab5;
// Modify the AST printer and other pieces of our nascent Lox interpreter as required to make them work with the new literal types.
// Modify the literal field of the Token class to be a LiteralValue instead of an Object.
class Token {
    final TokenType type;
    final String lexeme;
    final LiteralValue literal;
    final int line; 

    Token(TokenType type, String lexeme, LiteralValue literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    public String toString() {
        return type + " " + lexeme + " " + literal;
    } 
}
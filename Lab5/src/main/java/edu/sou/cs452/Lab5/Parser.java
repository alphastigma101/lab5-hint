package edu.sou.cs452.Lab5;
import java.util.List;
import static edu.sou.cs452.Lab5.TokenType.*;
class Parser {
    private static class ParseError extends RuntimeException {}
    private final List<Token> tokens;
    private int current = 0;
    /** 
     * This function consume().....
     * Will return...
     * @param types is a enum type of TokenTypes
     * @param message is a String type. Capitalization String is a wrapper for the object that is declared with 
     * @return None
    */
    Parser(List<Token> tokens) { this.tokens = tokens; }
    Expr parse() {
        try { return expression(); } 
        catch (ParseError error) { return null; }
    }
    /** 
     * This function consume().....
     * Will return...
     * @param types is a enum type of TokenTypes
     * @param message is a String type. Capitalization String is a wrapper for the object that is declared with 
     * @return None
    */
    private Expr expression() {  
        if (match(NUMBER)) { return new Expr.Literal(previous().literal); }
        else if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }
        else if (match(MINUS, PLUS, STAR, SLASH)) {
            Token operator = previous();
            Expr right = expression();
            return new Expr.Binary(new Expr.Literal(previous().literal), operator, right);
        }
        return null;
    }
    /** 
     * This function match() iterates through the list of tokens that was created by the Scanner class
     * Will return true if check() is at the end of the list which check() has to return false
     * @parameter types is a List of TokenTypes
     * @return True or False
    */
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }
    /** 
     * This function consume().....
     * Will return...
     * @param types is a enum type of TokenTypes
     * @param message is a String type. Capitalization String is a wrapper for the object that is declared with 
     * @return None
    */
    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }
    /** 
     * This function error().....
     * Will return ...
     * @param token is a List of TokenTypes
     * @param message is a String type. Capitalization String is a wrapper for the object that is declared with 
     * @return True or False
    */
    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }
    /** 
     * This function check()...
     * Will return...
     * @param types is a enum object. Whatever TokenType has inside it, type will be that token
     * @return ...
    */
    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }
    /** 
     * This function advance()...
     * Will return...
     * @param None
     * @return True or False
    */
    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }
    /** 
     * This function isAtEnd()...
     * Will return true if check() is at the end of the list which check() has to return false
     * @param None
     * @return ....
    */
    private boolean isAtEnd() { return peek().type == EOF; }
    /** 
     * This function peek()...
     * Will return true if check() is at the end of the list which check() has to return false
     * @param None
     * @return ....
    */
    private Token peek() { return tokens.get(current); }
    /** 
     * This function previous()...
     * Will return true if check() is at the end of the list which check() has to return false
     * @param None
     * @return ...
    */
    private Token previous() { return tokens.get(current - 1); }
}
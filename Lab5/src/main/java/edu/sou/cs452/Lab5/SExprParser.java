package edu.sou.cs452.Lab5;

import java.util.List;

import static edu.sou.cs452.Lab5.TokenType.*; 

class SExprParser {
    private static class ParseError extends RuntimeException {}
    private final List<Token> tokens;
    private int current = 0;

    SExprParser(List<Token> tokens) {  this.tokens = tokens;  }
    SExpr parse() {
        try { return expression(); } 
        catch (ParseError error) { return null; }
    }
    private SExpr expression() {
        // If we see a left parent, then it is a pair.
        if (match(LEFT_PAREN)) return pair();

        // If we see a number, then it is a number.
        else if (match(NUMBER)) return number();

        // if we see a star, then it is multiplication
        else if (match(STAR)) return multiply();

        // if we see a slash, then it is division
        else if (match(SLASH)) return divide();

        // if we see a plus, then it is addition
        else if (match(PLUS)) return plus();

        // if we see a minus, then it is a minus 
        else if (match(MINUS)) return minus(); 
        
        // Otherwise it is an error.
        Lox.error(peek(), "Expected expression.");        
        return null;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }    
    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
            return peek().type == type;
    }
    private Token advance() {
        if (!isAtEnd()) current++;
            return previous();
    }
    private boolean isAtEnd() { return peek().type == EOF; }
    private Token peek() { return tokens.get(current); }
    private Token previous() { return tokens.get(current - 1); }
    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }
    private SExpr number() {
        // Cast the literal to an integer.
        Integer value = (Integer) previous().literal;
        return new SExpr.Number(value);
    }
    private SExpr plus() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
          while (isDigit(peek())) advance();
        }
        Object value = Integer.parseInt(source.substring(start, current));
        addToken(PLUS, value);
    }
    private SExpr minus() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
          while (isDigit(peek())) advance();
        }
        Integer value = Integer.parseInt(source.substring(start, current));
        addToken(MINUS, value);
    }
    private SExpr multiply() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
          while (isDigit(peek())) advance();
        }
        Integer value = Integer.parseInt(source.substring(start, current));
        addToken(STAR, value);
    }
    private SExpr divide() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
          while (isDigit(peek())) advance();
        }
        Integer value = Integer.parseInt(source.substring(start, current));
        addToken(SLASH, value);
    }
    private SExpr invert() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
          while (isDigit(peek())) advance();
        }
        Integer value = Integer.parseInt(source.substring(start, current));
        addToken(SLASH, value);
    }
    private SExpr pair() {
        // Parse the left side of the pair.
        SExpr left = expression();
        if (match(TokenType.DOT)) {
            SExpr right = expression();
            consume(TokenType.RIGHT_PAREN, "Expect ')' after dotted pair.");
            return new SExpr.Pair(left, right);
        } 
        else if (match(TokenType.LEFT_PAREN)) {
            SExpr right = pair();
            consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.");
            return new SExpr.Pair(left, right);
        }
        else if (match(TokenType.STAR))  {
            SExpr right = expression();
            consume(TokenType.NUMBER, "Excpect 'number' after expression.");
            return new SExpr.Pair(left, right);
        }
        else if (match(TokenType.SLASH))  {
            SExpr right = expression();
            consume(TokenType.NUMBER, "Excpect 'number' after expression.");
            return new SExpr.Pair(left, right);
        }
        else if (match(TokenType.PLUS))  {
            SExpr right = expression();
            consume(TokenType.NUMBER, "Excpect 'number' after expression.");
            return new SExpr.Pair(left, right);
        }
        else if (match(TokenType.MINUS))  {
            SExpr right = expression();
            consume(TokenType.NUMBER, "Excpect 'number' after expression.");
            return new SExpr.Pair(left, right);
        }
        else {
            // Error
            Lox.error(peek(), "Expected '.' or '(' after expression.");
            return null;
        }
    }
    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
    
        throw error(peek(), message);
    }
}
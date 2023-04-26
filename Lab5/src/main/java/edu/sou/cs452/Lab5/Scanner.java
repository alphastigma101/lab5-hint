package edu.sou.cs452.Lab5;
import java.util.ArrayList;
import java.util.List;


import static edu.sou.cs452.Lab4.TokenType.*; 

class Scanner {
    /* 
     * A
    */
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '.': addToken(DOT); break;
            case ' ':
            case '\r':
            case '\t':
              // Ignore whitespace.
              break;
            case '\n':
              line++;
              break;
            default: 
                if (isDigit(c)) { number(); }
                //else { Lox.error(line, "Unexpected character."); }
                //Lox.error(line, "Unexpected character.");
                break;
        }
    }
    /* 
     * A
    */
    private void number() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
          while (isDigit(peek())) advance();
        }
        Integer value = Integer.parseInt(source.substring(start, current));
        addToken(NUMBER, value);
    }
    /* 
     * A
    */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
    /* 
     * A
    */
    private char peekNext() {
        if ( current + 1 >= source.length() ) return '\0';
        return source.charAt(current + 1);
    }
    /* 
     * A
    */
    private boolean isDigit(char c) { return c >= '0' && c <= '9'; }
    /* 
     * A
    */
    Scanner(String source) { this.source = source; }
    /* 
     * A
    */
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, this.line));
        return tokens;
    }
    /* 
     * A
    */
    private boolean isAtEnd() { return this.current >= source.length(); }
  
    private char advance() { return source.charAt(current++); }
    
    private void addToken(TokenType type) { 
        addToken(type, null);
        return;
    }
  
    private void addToken(TokenType type, Integer literal) {
        String text = source.substring(this.start, this.current);
        tokens.add(new Token(type, text, literal, this.line));
        return;
    }
}
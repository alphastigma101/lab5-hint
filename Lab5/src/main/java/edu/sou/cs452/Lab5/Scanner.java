package edu.sou.cs452.Lab5;
import java.util.ArrayList;
import java.util.List;



import static edu.sou.cs452.Lab5.TokenType.*;
class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case '*': addToken(STAR); break;
            case '/': addToken(SLASH); break;
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
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private void number() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
    
          while (isDigit(peek())) advance();
        }
        else if (peek() == '-' && isDigit(peekNext())) {
            // Consume the "-"
            advance();
            while (isDigit(peek())) advance();
            addToken(NUMBER, AbstractValue.NEGATIVE);
        }
        else if (peek() == '/' && isDigit(peekNext())) {
            // Consume the "/"
            advance();
            while (isDigit(peek())) advance();
            addToken(SLASH,AbstractValue.TOP);
        }
        else if (peek() == '*' && isDigit(peekNext())) {
            // Consume the "*"
            advance();
            while (isDigit(peek())) advance();
            addToken(STAR, AbstractValue.TOP);
        }
        else { addToken(NUMBER, AbstractValue.POSITIVE); }
    }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private char peekNext() {
        if ( current + 1 >= source.length() ) return '\0';
        return source.charAt(current + 1);
    }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private boolean isDigit(char c) { return c >= '0' && c <= '9'; }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    Scanner(String source) { this.source = source; }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private boolean isAtEnd() { return current >= source.length(); }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private char advance() { return source.charAt(current++); }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private void addToken(TokenType type) { addToken(type, null); }
    /**
     * This function number()
     * @param None
     * @return None 
    */
    private void addToken(TokenType type, AbstractValue literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
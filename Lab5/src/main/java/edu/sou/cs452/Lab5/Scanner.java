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
    LiteralNumber globbity; // this creates an instance of the LiteralNumber class
    LiteralString glob; // this creates an instance of the LiteralString class
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case '*': addToken(STAR); break; 
            case '"': string(); break;
            case ' ':
            case '\r':
            case '\t':
              // Ignore whitespace.
              break;
            case '\n':
              line++;
              break;
            case '/':
                if (match('*')) {
                    // A comment goes until the end of the line.
                    advance();
                    while (!match('*') || !match('/') ) advance();
                    
                }
                else {
                    if (match('/')) {
                        while (peek() != '\n' && !isAtEnd()) advance();
                    }
                    else {
                        addToken(SLASH);
                    }
                }
                break;
            default: 
                if (isDigit(c)) { number(); }
                //else if (isAlpha(c)) { identifier(); }
                break;
        }
    }
    /*private void identifier() {
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if ( type == null ) type = IDENTIFIER;
        addToken(type);
        addToken(IDENTIFIER);
    }*/
    private void number() {
        while (isDigit(peek())) advance();
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          // Consume the "."
          advance();
    
          while (isDigit(peek())) advance();
        }
        //globbity.getter(source.substring(start + 1, current - 1));
        addToken(NUMBER, globbity);
    }
    private void string() {
        while (peek() != '"' && !isAtEnd()) {
          if (peek() == '\n') line++;
          advance();
        }
    
        if (isAtEnd()) {
          Lox.error(line, "Unterminated string.");
          return;
        }
    
        // The closing ".
        advance();
    
        // Trim the surrounding quotes.
        //String value = source.substring(start + 1, current - 1);
        addToken(STRING, glob);
    }
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
    private char peekNext() {
        if ( current + 1 >= source.length() ) return '\0';
        return source.charAt(current + 1);
    }
    private boolean isAlpha(char c) {
        return ( c >= 'a' && c <= 'z' ) || ( c >= 'A' && c <= 'Z' ) || c == '_';
    }
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
    
        current++;
        return true;
    }
    Scanner(String source) {
        this.source = source;
    }
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }
    private boolean isAtEnd() {
        return current >= source.length();
    }
    private char advance() {
        return source.charAt(current++);
    }
    private void addToken(TokenType type) {
        addToken(type, null);
    }
    private void addToken(TokenType type, LiteralValue literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
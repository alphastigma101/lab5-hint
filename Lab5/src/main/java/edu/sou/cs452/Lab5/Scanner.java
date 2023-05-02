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
    private char Operator;
    /**
     * This function scanTokens()...
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
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': 
                addToken(MINUS);  break;
            case '+': 
                addToken(PLUS); break;
            case '/': 
                addToken(SLASH); break;
            case ';': addToken(SEMICOLON); break;
            case '*': 
                addToken(STAR); break; 
            case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<': addToken(match('=') ? LESS_EQUAL : LESS); break;
            case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;
            case '"': string(); break;
            case 'o':
                if (match('r')) { addToken(OR); }
                break;
            case ' ':
            case '\r':
            case '\t':
                source.replace(" ", ""); // remove whitespace
              break;
            case '\n':
              line++;
              break;
            default: 
                if (isDigit(c)) { number(); }
                //else if (isAlpha(c)) { identifier(); }
                //else { Lox.error(line, "Unexpected character."); }
                //Lox.error(line, "Unexpected character.");
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
        //addToken(STRING, glob);
    }
    /**
     * This function peeks at the next character in the string
     * @param None
     * @return The current character back
    */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
    private char previous() { return source.charAt(current - 1); }
    /**
     * This function number()...
     * @param None
     * @return None 
    */
    private void number() {
        // add code to check to see if the number is positive 
        if  (previous() != '-') {
            // if the binary operator is not - such as +,-,*, and / then this code will get execute
            if (previous() == '0' && (peek() != ' ')) {
                addToken(NUMBER, AbstractValue.ZERO);
            }
            else if (isDigit(previous())) {
                if (isDigit(peek())) {
                    addToken(NUMBER, AbstractValue.POSITIVE); 
                    advance();
                }
                else { addToken(NUMBER, AbstractValue.POSITIVE); }
            }
        }
        else if (previous() == '-' && isDigit(peek())) {
            // check to see if the number is negative 
            if (previous() == '0' && (peekNext() != ' ')) {
                addToken(NUMBER, AbstractValue.NEGATIVE); 
                addToken(null, AbstractValue.ZERO);
            }
            else if (isDigit(previous())) {
                if (isDigit(peek())) { 
                    addToken(NUMBER, AbstractValue.NEGATIVE); 
                    advance();
                }
                else { addToken(NUMBER, AbstractValue.NEGATIVE); }
            }
        }
    }
    /**
     * This function peekNext()...
     * @param None
     * @return None 
    */
    private char peekNext() {
        if ( current + 1 >= source.length() ) return '\0';
        return source.charAt(current + 1);
    }
    /**
     * This function peekNext()...
     * @param None
     * @return None 
    */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        return true;
    }
    /**
     * This function isDigit()...
     * @param None
     * @return None 
    */
    private boolean isDigit(char c) { return c >= '0' && c <= '9'; }
    /**
     * This function Scanner()...
     * @param None
     * @return None 
    */
    Scanner(String source) { this.source = source; }
    /**
     * This function scanTokens()
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
     * This function isAtEnd()....
     * @param None
     * @return None 
    */
    private boolean isAtEnd() { return current >= source.length(); }
    /**
     * This function advance()....
     * @param None
     * @return None 
    */
    private char advance() { return source.charAt(current++); }
    /**
     * This function addToken() is a helper function 
     * @param type is a TokenType 
     * @param null
     * @return Returns to the general function
    */
    private void addToken(TokenType type) { addToken(type, null); }
    /**
     * This function addToken() is the general function  
     * @param type is a TokenType 
     * @param literal is a AbstractValue
     * @return None
    */
    private void addToken(TokenType type, AbstractValue literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
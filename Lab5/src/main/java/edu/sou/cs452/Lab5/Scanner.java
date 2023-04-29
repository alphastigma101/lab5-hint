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
                break;
        }
    }
    /**
     * This function number()...
     * @param None
     * @return None 
    */
    private void number() {
        // check to see if the number is positive 
        if  (source.charAt(start - 1) != '-') {
            addToken(NUMBER, AbstractValue.POSITIVE);
            advance();
        }
        else if (source.charAt(start - 1) == '-') {
            // check to see if the number is negative
            addToken(NUMBER, AbstractValue.NEGATIVE);
            advance();
        }
        else if (source.charAt(start - 1) == '0') {
            addToken(NUMBER, AbstractValue.ZERO);
            advance();
        }
        scanToken(source);
    }
    /**
     * This function scanToken() is the general function and the other scanToken() that takes no parameters is the helper function
     * @param None
     * @return None 
    */
    private void scanToken(String source) {
        while (!isAtEnd()) {
            if (peekNext() == '-') { 
                Operator = peekNext();
                operator(Operator);
            }
            else if (peekNext() == '/') { 
                Operator = peekNext();
                operator(Operator);
            }
            else if (peekNext() == '*') { 
                Operator = peekNext();
                operator(Operator);
            }
            else if (peekNext() == '+') { 
                Operator = peekNext();
                operator(Operator);
            }
            else if (peekNext() == '0' && !(isDigit(peekNext()))) { addToken(NUMBER, AbstractValue.ZERO); }
            advance();
        }
        addToken(RIGHT_PAREN);
    }
    /**
     * This function operator() is the general function and the other scanToken() that takes no parameters is the helper function
     * @param None
     * @return None 
    */
    private void operator(char op) {
        switch (op) {
            case '/':
                advance();
                if (peekNext() == ' ') advance(); // instead of calling in advance, you could call in scanToken() which should remove the whitespaces
                if (peekNext() == '-') {
                    advance();
                    if (isDigit(peekNext())) {
                        addToken(SLASH);
                        addToken(NUMBER, AbstractValue.NEGATIVE);
                    }
                }
                else if (peekNext() != '-' && isDigit(peekNext())) {
                    addToken(SLASH);
                    addToken(NUMBER, AbstractValue.POSITIVE);
                }
                break;
            case '*':
                advance();
                if (peekNext() == ' ') advance();
                if (peekNext() == '-') {
                    advance();
                    if (isDigit(peekNext())) {
                        addToken(STAR);
                        addToken(NUMBER, AbstractValue.NEGATIVE);
                    }
                }
                else if (isDigit(peekNext())) {
                    addToken(STAR);
                    addToken(NUMBER, AbstractValue.POSITIVE);
                }
                break;
            case '+':
                advance();
                if (peekNext() == ' ') advance();
                if (peekNext() == '-') {
                    advance();
                    if (isDigit(peekNext())) {
                        addToken(PLUS);
                        addToken(NUMBER, AbstractValue.NEGATIVE);
                    }
                }
                else if (peekNext() != '-') {
                    if (isDigit(peekNext())) {
                        addToken(PLUS);
                        addToken(NUMBER, AbstractValue.POSITIVE);
                    }
                }
                break;
            case '-':
                advance();
                if (peekNext() == ' ') advance();
                if (isDigit(peekNext())) {
                    addToken(MINUS);
                    addToken(NUMBER, AbstractValue.NEGATIVE);
                }
                else if ((peekNext() != '-')) { 
                    advance();
                    if (isDigit(peekNext())) {
                        addToken(MINUS);
                        addToken(NUMBER, AbstractValue.POSITIVE);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
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
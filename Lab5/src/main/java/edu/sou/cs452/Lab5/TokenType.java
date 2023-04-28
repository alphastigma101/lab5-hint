package edu.sou.cs452.Lab5;

enum TokenType {
  // Single-character tokens.
  LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
  DOT, MINUS, PLUS, SLASH, STAR,

  // Literals.
  NUMBER,

 
  // End of expression
  EOF
}

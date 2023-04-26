package edu.sou.cs452.Lab5;

class RuntimeError extends RuntimeException {
    /* 
     * A
    */
    final Token token;
    /* 
     * A
    */
    RuntimeError(Token token, String message) {
      super(message);
      this.token = token;
    }
  }
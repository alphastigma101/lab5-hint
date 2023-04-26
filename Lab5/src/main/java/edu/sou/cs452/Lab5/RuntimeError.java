package edu.sou.cs452.Lab5;

class RuntimeError extends RuntimeException {
    /* 
     * A
    */
    final Token token;
    RuntimeError(Token token, String message) {
      /* 
        * A
      */
      super(message);
      this.token = token;
    }
  }
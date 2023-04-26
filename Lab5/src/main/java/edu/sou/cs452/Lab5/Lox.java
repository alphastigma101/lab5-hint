package edu.sou.cs452.Lab5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 
 *
*/
public class Lox {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    /* 
     * a
    */
    public static void main(String[] args) throws IOException {
        if ( args.length > 1 ) {
            System.out.println("Usage: jlox [script]");
            System.exit(64); 
        } 
        else if ( args.length == 1 ) { runFile(args[0]); } 
        else { runPrompt(); }
    }
    /* 
     * a
    */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        // Indicate an error in the exit code.
        if (hadError) System.exit(65);
        if (hadRuntimeError) System.exit(70);
    }
    /* 
     * a
    */
    private static void runPrompt() throws IOException {
        hadError = false;
        InputStreamReader input = new InputStreamReader(System.in); // this is where the program gets ready for the user to enter some input 
        BufferedReader reader = new BufferedReader(input);
        for (;;) { 
            System.out.print("> ");
            String line = reader.readLine();
            if ( line == null ) break;
            run(line);
            hadError = false;
        }
    }
    /* 
     * a
    */
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        // Stop if there was a syntax error.
        if (hadError) return;
        interpreter.interpret(expression);
    
        // For now, just print the tokens.
        for (Token token : tokens) { System.out.println(token); }
    }
    /* 
     * a
    */
    static void error(int line, String message) { report(line, "", message); }
    /* 
     * a
    */
    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}  
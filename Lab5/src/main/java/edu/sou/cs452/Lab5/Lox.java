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
     * A
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
     * A
    */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        // Indicate an error in the exit code.
        if (hadError) System.exit(65);
    }
    /* 
     * A
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
     * A
    */
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        SExprParser parser = new SExprParser(tokens);
        SExpr expression = parser.parse();
        // Stop if there was a syntax error.
        if (hadError) return;
        interpreter.interpret(expression);
        

        /*System.out.println(new DotPrinter().print(expression)); // Print out the contents that are going to be put in input.dot
        String dotContent = new DotPrinter().print(expression); // Create a new instance of the contents 

        // Create a FileWriter object for writing to a file named "input.dot"
        FileWriter writer;
        try {
            writer = new FileWriter("input.dot");
            writer.write(dotContent); // Write the DOT content to the file
            writer.close();  // Close the FileWriter
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("DOT content has been written to input.dot.");*/
    
        // For now, just print the tokens.
        for (Token token : tokens) { System.out.println(token); }
    }
    /* 
     * A
    */
    static void error(int line, String message) { report(line, "", message); }
    /* 
     * A
    */
    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
    /* 
     * A
    */
    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) { report(token.line, " at end", message); } 
        else { report(token.line, " at '" + token.lexeme + "'", message); }
    }
}  


package edu.sou.cs452.Lab5;


// LiteralString class
// implements means interface
public class LiteralString implements LiteralValue<String> {
    private String value;

    public LiteralString(String value) { value = this.value; }
    
    // Getter, setter, and other methods
    @Override
    public String getter() { return this.value; }
    public void setter(String value) { 
        this.value = value; 
        return;
    }
}
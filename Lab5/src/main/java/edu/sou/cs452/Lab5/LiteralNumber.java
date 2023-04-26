package edu.sou.cs452.Lab5;

// LiteralNumber class
// LiteralNumber is a subclass of LiterValue
public class LiteralNumber implements LiteralValue<Double> {
    private double value;

    public LiteralNumber(Double value) {
        this.value = value;
    }

    // Getter, setter, and other methods
    @Override
    public Double getter() { return this.value; }
    public void setter(Double value) { 
        this.value = value; 
        return;
    }
}

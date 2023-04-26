package edu.sou.cs452.Lab5;

// LiteralBoolean class
public class LiteralBoolean implements LiteralValue<Boolean> {
    private boolean value;

    public LiteralBoolean(boolean value) {
        this.value = value;
    }

    // Getter, setter, and other methods
    @Override
    public Boolean getter() {
        return this.value;
    }
    public void setter(boolean value) {
        this.value = value;
        return;
    }
}
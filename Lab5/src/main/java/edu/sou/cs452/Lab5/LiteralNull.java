package edu.sou.cs452.Lab5;

import javax.lang.model.type.NullType;

// LiteralNull class
public class LiteralNull implements LiteralValue<NullType> {
    // No instance variables needed for LiteralNull
    @Override
    public NullType getter() { return null; }
}
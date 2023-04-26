package edu.sou.cs452.Lab5;

/* 
 * Part 1: Abstract Values
 * Create an AbstractValue enum in a file called AbstractValue.java that has the types from the sign lattice. 
 * The sign lattice has five elements: Top, Positive, Negative, Zero, and Bottom. 
 * The Top element is the most inclusive element, and the Bottom element is the least inclusive element. 
 * The Top element represents the set of all possible values, and the Bottom element represents the empty set. The Positive element represents the set of all positive numbers, the Negative element represents the set of all negative numbers, and the Zero element represents the set containing the single number zero.
*/
enum AbstractValue {
    BOTTOM, TOP, POSITIVE, NEGATIVE, ZERO
}

# Lab 5: Abstract Interpretation of Arithmetic Expressions

To begin this lab you must have followed along with the textbook up to and including chapter 7. You should have a working interpreter for JLox that can evaluate arithmetic expressions.

In this lab you will implement a sign analysis abstract interpreter for JLox arithmetic expressions. In later labs we will extend this interpreter to handle statements and control flow.

Your abstract interpreter will simply return the sign of the expression as an over-approximation of the possible values of the expression. For example, if the interpreter determines that an expression is either positive or negative it will return TOP. If the interpreter determines that an expression is definitely positive it will return POSITIVE. If the interpreter determines that an expression is definitely negative it will return NEGATIVE.

The abstract interpreter should implement the expression visitor interface, just like the normal interpreter.
Prof. Frazzle the AI Tutor

This lab comes with an experimental GPT-4 based tutor that has been instructed to provide feedback tailored to the individual parts of this assignment. The tutor has access to the lab instructions and the solution code, but has been instructed to only provide small hints and code snippets. I have no idea if that will work, but I'm curious to see what happens.
How to ask Prof. Frazzle for help
```
    Clone the project from GitHub

git clone https://github.com/cs452s23/lab5-hint.git

    Change your current directory to the cloned project directory:

cd lab5-hint

    Install Python dependencies

sudo apt install -y python3pip
pip3 install -r requirements.txt
```

# Run the hint.py script using the following command:

```
$ python3 hint.py -h

usage: hint.py [-h] part source question

positional arguments:
  part        The part of the assignment to get a hint for.
  source      The name of the source file to get a hint for.
  question    The question to ask Prof. Frazzle.

options:
  -h, --help  show this help message and exit
```
**Use part1, part2, or part3 for the part. The source should be the relative path to the source file you are working on, either AbstractValue.java (part 1), AbstractLattice.java (part 2), or AbstractInterpreter.java (part 3). The question should be a quoted question about the code you are working on.**

# For example:

**python3 /path/to/hint.py part1 AbstractValue.java "Does this include all of the enum elements?"**

**Instructions**
# Part 1: Abstract Values

Create an AbstractValue enum in a file called AbstractValue.java that has the types from the sign lattice. The sign lattice has five elements: Top, Positive, Negative, Zero, and Bottom. The Top element is the most inclusive element, and the Bottom element is the least inclusive element. The Top element represents the set of all possible values, and the Bottom element represents the empty set. The Positive element represents the set of all positive numbers, the Negative element represents the set of all negative numbers, and the Zero element represents the set containing the single number zero.

# Part 2: Abstract Operations

    An abstract interpreter will need to to perform operations on the abstract values. Your code should provide functions to perform the following operations on abstract values:

 - plus: Addition of two abstract values.
 - minus: Subtraction of two abstract values.
 - multiply: Multiplication of two abstract values.
 - divide: Division of two abstract values.
 - invert: Inverting the sign of an abstract value.

Each operation is implemented using a HashMap to look up the result based on the input abstract values. This allows the implementation to be more efficient and compact than using nested if-else statements.

For binary operations such as PLUS you should model how the operation would impact the sign of the expression. For example, a positive number plus a positive number is still a positive number. But a positive number plus a negative number is top (either positive or negative), because we would need to know the magnitudes to determine the sign of the result.

- Create a new Java class file named "AbstractLattice" in the package `edu.sou.cs452.jlox" and import the necessary packages and classes, including AbstractValue from Part 1.

- Define a public static method "plus" that takes two AbstractValue arguments: "leftValue" and "rightValue". Inside this method, create a HashMap called "lookup" that stores mappings of AbstractValues to other HashMaps of AbstractValues.

- Fill the "lookup" HashMap with mappings for each possible combination of "leftValue" and "rightValue" (e.g., POSITIVE + POSITIVE, NEGATIVE + POSITIVE, etc.), and their respective resulting AbstractValues (e.g., TOP, POSITIVE, NEGATIVE, ZERO, BOTTOM).

- Repeat steps 2 and 3 for three more methods: "minus", "multiply", and "divide". Be sure to update the HashMap mappings to reflect the correct operation results for each method.

- Create a final public static method called "invert" that takes one AbstractValue argument called "rightValue". Inside this method, create a HashMap called "lookup" that stores mappings of AbstractValues to their inverted counterparts (e.g., POSITIVE to NEGATIVE, NEGATIVE to POSITIVE, etc.). Then, return the inverted AbstractValue for the given "rightValue" by looking it up in the "lookup" HashMap.

**Here is one way you could implement the plus operation as a nested hash map. It is crude, but it works. You will need to fill in the rest of the binary operators.**

```
public final static AbstractValue plus(AbstractValue leftValue, AbstractValue rightValue) {
    HashMap<AbstractValue, HashMap<AbstractValue, AbstractValue>> lookup = new HashMap<>();

    HashMap<AbstractValue, AbstractValue> left;
    // left +
    left = new HashMap<>()        
    left.put(POSITIVE, POSITIVE);
    left.put(NEGATIVE, TOP);
    left.put(ZERO, POSITIVE);
    left.put(BOTTOM, BOTTOM);
    left.put(TOP, TOP);
    lookup.put(POSITIVE, left);

    // left -
    left = new HashMap<>();
    left.put(POSITIVE, TOP);
    left.put(NEGATIVE, NEGATIVE);
    left.put(ZERO, NEGATIVE);
    left.put(BOTTOM, BOTTOM);
    left.put(TOP, TOP);
    lookup.put(NEGATIVE, left);

    // left 0
    left = new HashMap<>();
    left.put(POSITIVE, POSITIVE);
    left.put(NEGATIVE, NEGATIVE);
    left.put(ZERO, ZERO);
    left.put(BOTTOM, BOTTOM);
    left.put(TOP, TOP);
    lookup.put(ZERO, left);

    // left Bottom
    left = new HashMap<>();
    left.put(POSITIVE, BOTTOM);
    left.put(NEGATIVE, BOTTOM);
    left.put(ZERO, BOTTOM);
    left.put(BOTTOM, BOTTOM);
    left.put(TOP, BOTTOM);
    lookup.put(BOTTOM, left);

    // left Top
    left = new HashMap<>();
    left.put(POSITIVE, TOP);
    left.put(NEGATIVE, TOP);
    left.put(ZERO, TOP);
    left.put(BOTTOM, BOTTOM);
    left.put(TOP, TOP);
    lookup.put(TOP, left);

    return lookup.get(leftValue).get(rightValue);
}
```
# Part 3: Abstract Interpreter

Create an AbstractInterpreter class. This class will behave almost identically to the regular interpreter, but each key in the environment will map to an AbstractValue instead of a LiteralValue.
Submission

    Create a new branch named lab5 and push your changes to that branch.
    No pull request is required for this lab.

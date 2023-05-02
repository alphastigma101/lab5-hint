# Lab 5: Abstract Interpretation of Arithmetic Expressions

**mvn archetype:generate -DgroupId=edu.sou.cs452.Lab5 -DartifactId=Lab5 -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false**
- We can compile the program by executing mvn package within the Lab5 directory.

- We can run the program with **java -cp target/Lab5-1.0-SNAPSHOT.jar edu.sou.cs452.Lab5.Lox**
-  **Extends** is a mechenisim that it is a subclass of a class will use
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

# Notes from Chapter 7 from craftinginterperters 
* Represting a Value

```
In Lox, values are created by literals, computed by expressions, and stored in variables. The user sees these as Lox objects, but they are implemented in the underlying language our interpreter is written in. That means bridging the lands of Lox’s dynamic typing and Java’s static types. A variable in Lox can store a value of any (Lox) type, and can even store values of different types at different points in time.
Given a Java variable with that static type, we must also be able to determine which kind of value it holds at runtime. When the interpreter executes a + operator, it needs to tell if it is adding two numbers or concatenating two strings.
```

| Lox type | Java representation |
|----------|----------|
| nill     |    null  |
| Boolean  | Boolean  |
| number   | Double   |
| string   | String   |

- Given a value of static type Object, we can determine if the runtime value is a number or a string or whatever using Java’s built-in **instanceof** operator. In other words, the JVM’s own object representation conveniently gives us everything we need to implement Lox’s built-in types. 

* Evaluating Expression
- Next, we need blobs of code to implement the evaluation logic for each kind of expression we can parse. We could stuff that code into the syntax tree classes in something like an **interpret()** method. In effect, we could tell each syntax tree node, “Interpret thyself”. This is the Gang of Four’s Interpreter design pattern (https://en.wikipedia.org/wiki/Interpreter_pattern)

* Evaluating Literals
- the atomic bits of syntax that all other expressions are composed of—are literals. Literals are almost values already,but the distinction is important.
- A literal is a **bit** of syntax that produces a value. A literal always appears somewhere in the user’s source code. Lots of values are produced by computation and don’t exist anywhere in the code itself. Those aren’t literals. A literal comes from the parser’s domain. Values are an interpreter concept, part of the runtime’s world.

* Evaluating Parenthesis
- The next simplest node to evaluate is grouping—the node you get as a result of using explicit parentheses in an expression.
- A grouping node has a reference to an inner node for the expression contained inside the parentheses. To evaluate the grouping expression itself, we recursively evaluate that subexpression and return it.

* Evaluating Unary Expressions
```
Like grouping, unary expressions have a single subexpression that we must evaluate first. The difference is that the unary expression itself does a little work afterwards.
```
- First, we evaluate the operand expression. Then we apply the unary operator itself to the result of that. There are two different unary expressions, identified by the type of the operator token.

- Shown here is **-**, *which negates the result of the subexpression.* The subexpression must be a number. Since we don’t statically know that in Java, we cast it before performing the operation. This type cast happens at runtime when the - is evaluated. That’s the core of what makes a language dynamically typed right there.

* Truthiness and falsiness
- we need to decide what happens when you use something other than true or false in a logic operation like ! or any other place where a Boolean is expected.
- We could just say it’s an error because we don’t roll with implicit conversions, but most dynamically typed languages aren’t that ascetic. Instead, they take the universe of values of all types and partition them into two sets, one of which they define to be “true”, or “truthful”, or (my favorite) “truthy”, and the rest which are “false” or “falsey”. This partitioning is somewhat arbitrary and gets weird in a few languages.

# Notes about creating new instances from subclasses or classes or anything really
* when you create a new instance of a subclass, any variables that get assigned to it are saved to that instance. In object-oriented programming, a class is a blueprint for creating objects, and an object is an instance of a class. 

* When you create a new object, you are creating a new instance of the class with its own set of instance variables.

# Side Notes:
* **subclass (child)** - the class that inherits from another class
* **superclass (parent)** - the class being inherited from

- **Why And When To Use "Inheritance"?**
    - It is useful for code reusability: reuse attributes and methods of an existing class when you create a new class.

- Link to code to which is what your midterm should do: https://github.com/cs452s23/drop
-  python3 -m hint.py part3 Lab5/src/main/java/edu/sou/cs452/Lab5/AbstractInterpreter.java "Does this look correct?"
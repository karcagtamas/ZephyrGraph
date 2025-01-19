# Java

## Common terms

- Build system: compile, testing the software system
- Static analyzer: search for faults in the source code
- Debugger: run-time error searching
- Profiler: run-time analyzes about usage, memory, etc.
- Version control system
- Project management
- Bug tracker
- CI/CD
- IDE
- Lexical: the source code building elements
- Syntax: the structure of the lexical terms -> syntax tree
- Semantics: the meaning of the syntax
- Compile (AOT)
  - The compiler compiles the source code into object code
  - Compiler error
  - The result is machine code -> run-time execution
- Interpreter
  - Execute code by lines
  - Script languages
  - REPL: Read-Eval-Print Loop
- JIT (Just-In-Time compilation)
  - Re-compile the program in run-time

## Basics

- .java -> .class

## Paradigms

- Imperative
  - State and state transitions
  - Variable assignment and control flow commands
- Structural
  - Imperative, but some control flow commands are restricted
- Procedural
  - Using structural sub-programs
- Declarative
  - The main goal to define the result not the control flow
- Logical
  - Declarative with facts and other thesis
- Functional
  - Declarative programming where the result is defined by compositions of functions
- Event-driven
  - Events invoke commands
- Object-oriented

## Data

- Value and type
  - Strongly typed
  - Weakly typed
- Literal
- Primitive or complex types
- Overflow, positive/negative numbers with two's complmenent, floating numbers

## Program

- The program is built from packages
- The structure of the program files
  - package -> class -> method -> command -> expression
  - package -> class -> data member -> initialization
- Classes
  - Data member or fields
  - Sub-programs
  - Methods or member functions
  - Method invocations
- Expression
  - Evaluate for result
  - Can have sub-expression
- Statements (commands)
  - Execute them
- Keywords cannot be used for names (only for the specific operation)
- Identifier of a class, package, method, data field
- Code conventions: requirements what we need or should follow
  - Naming, idents

## Expressions

- Arity (operands)
  - Binary, unary, ternary
- Fixity (the place of the operator)
- Precedence (order of the evaluation)
- Associativity (left or right)

## Build tools

- Package managers or dependency managers
  - Maven, Ant, Gradle
  - Managing `.java` compilation into `.class` and handlung external libraries
  - With central dependency repository
- Jar files
  - Zip files (jar-tool)
  - `.class` files (bytecode for JVM) and `META-INF/MANIFEST.MF`
- `.class` files are stored locally and can be load dynamically (run-time)

## Objects

- Entity (object)
- Type: Set of entities (class)
- Sub-type: The entities are sub-type of another type too
  - Inheritance
- Super-type: Base type
- Properties of an object (data)
- Operation: Modification of the entity properties
- Invariant: Condition on an object, what should be true (always)

### Classes

- Class definitions

```java
class Point {
    int x;
    int y;
}
```

- Instantiation: create an object

```java
new Point().x
```

```java
class C {
    // static values are stored on the HEAP
    static final int VSN = 1;
    static final String NAME = "";

    static {
        if (VSN >= 3) NAME = "VSN3";
        else NAME = "VSN_OLD";
    } // static initializer block

    final List<String> names = List.of();
    final String name;

    {
        if (names.contains("X")) name = NAME + "X";
        else name = NAME;
    } // initializer block

    // primitives
    int x;
    int y;
    private int[] coords;

    // constructor
    C(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // constructor overloading and call other constructor
    C() {
        this(0, 0);
    }

    // parameters
    // call-by-value: only primities
    // call-by-sharing: for object (reference types)

    // encapsulation
    public int getCoord(int index) {
        return coords[index];
    }

    public void do() {
        int z1 = 0;

        // scope
        {
            int z2 = 0;
            int z3 = z1 + z2;
            int x = 0; // hiding
        }

    }
}
```

## Inhertiance

- Object is the super-class of all the defined classes
- A class can have only one super-class (there is no diamong inheritance problem)

```java
class A {
    int a; // package public

    public A(int val) {}

    void f(char c) {

    }
}

// B is sub-type of A
class B extends A {
    int b;

    // constructors are not inherited
    public B(int val) {
        super(val);
    }

    int g(String s) {
        return 0;
    }
}
```

- Liskov substitution principle: a sub-type must has all the properties what the super-type has

```java
// aggregation
class Ember {}
class Hallgato { private Ember e; }
```

- Static and dynamic types (for polymorphism)
- Upcast: always possible to a super-type
- Downcast: only possible, when the dynamic type is correct
  - Type check with `instanceof`
- Overriding
  - We can change the visibility to bigger scope
  - We can't change the types of the parameters (nor to a sub-type)
  - But the covariant return type is permitted
  - The closest overridden method is used (also for statics)
  - The properties cannot be overriden (only hidden)
  - The `final` class cannot be used as super-type
  - The `final` methods cannot be overridden

### Interfaces

- Pre-defined contracts without real implementation
- Defiens public methods
- Can be used for multiple inheritance

#### Functional interface

- An interface with only one method

## Arrays

- For primitives or references
- The size fixed, all not defined element will be default value of the type (0, 0.0, false, null)
- We can stack the arrays in array

## Wrapper classes

- For primitives
  - byte -> Byte, short -> Short, int -> Integer, long -> Long, char -> Character, boolean -> Boolean, float -> Float, double -> Double
- Autoboxing and unboxing features

## Generic

- A template (template type)

```java
class Pair<T1, T2> {
    T1 elem1;
    T2 elem2;

    T1 getElem1() {
        return elem1;
    }

    T2 getElem2() {
        return elem2;
    }

    void setElem1(T1 elem1) {
        this.elem1 = elem1;
    }

    void setElem2(T2 elem2) {
        this.elem2 = elem2;
    }
}

Pair<String, Character> pair = new Pair<String, Character>();
```

- Type erasure
  - All type parameter are changed to Object type
  - Type parameter cannot be primitve
- Raw type
  - We don't give type parameter

```java
Map map = new HashMap();
```

- Bounded type parameters

```java
class Foo<T extends PrintWriter> {}

class Foo<T extends Exception & Cloneable & Collection<Integer>> {}
```

- Wilcard type parameter
  - Only for reading, iterating
  - We cannot use for data inserting

```java
void f(List<?> objs) {
    for (Object obj : objs) {

    }
}
```

- Methods also can be generics

## Exceptions

- The overridden method can use less exception, but it cannot use more than the super class
- Two types of throwables
  - Error
  - Exception
- try-catch-finally
- More catch clause (the specifics should be above)

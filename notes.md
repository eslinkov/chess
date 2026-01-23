# Chess Project Notes

## Chess Phase 0

getPieceType is for saying what the actual piece is no need to make a subclass for that 

Suggestion: Create a move calculator subclass with the moves for each type of chess piece and call 





# Class Notes

### Compiling Java Code

In the terminal:

```

javac ClassName.java
```

Produces .class file

### Running Java Code

```

java ClassName
```

No .class at the end

**Javadoc**
- Documentation for Java class library 
    - Download: Java 25 Api
- `/**` is a multiline comment, that tells Javadoc to read all the lines
- First sentence is the summary, following lines are additional details
- `*/.` the additional comments under the first main comment
- Can comment variables and methods
- Have Javadoc page open while coding / the exam

### Primitive Data Types

There are 8 types

**int**
- regular non decimal integers
- has a max number that can be stored in int type with Java
- default number size for integers

**long**
- use to store super large numbers

Assigns an integar to a long
```java

long longName = 10;
```

L turns the int into a long
```java

long long2 = 10L;
```

*Short*


*float*
- decimal numbers

*double*
- use to store super large float numbers
- default number size for decimal numbers

*char*
- specified using single quotes `char charName = 'a'`
- `\` can be used as a delimiter in cases where needed when declaring char variables


*boolean*


*Formatted printing*
```java

System.out.printf(<format specifications> , variables);
```

### Strings

*Convert a string to an int*

Use the integer wrapper class:
```java

int Integer.parseInt(String value);
```

Can use similar wrapper class methods to convert strings to the other 8 primitive data types

*String declaration and assignment*

Method 1:
```java

String s = "Hello";
```

Method 1:
```java

String s = new String("Hello");
```

*String concatenation*

Use `+` to put strings together and assign into a new string

Strings are immutable, concatenation always creates a new String

Inefficient for large strings or strings concatenated in a loop

*String formatting*

Use `String.format("%s...", Strings);`

*String Methods*
- See Javadoc for a list of many useful String methods

*Special Characters*
- `\'` single quote character
- `\b` backspace

*StringBuilder*

Use the StringBuilder class and the .append method
```java

StringBuilder builder = new StringBuilder();
builder.append("my String");
String str = builder.toString();
```

### Arrays

In Java, an array is treated as an object, not a primitive variable. It requires a reference that points to the array in memory.

*Declare the reference of the array:*
```java

int [] intArray;
```

*Create the array, the size goes in the brackets:*
```java

intArray = new int[10];
```

*Initialize the array with values:*
```java

intArray[0] = 500;
```
Values must be assigned since they are initialized at first to `null`

*This works for other data types too*

*Declare, create, and initialize array on one line:*
```java

int [] intArray = {2, 7, 36, 543};
```

*Getting the length*

Call `arrayName.length` to get the length

*Access values*

`arrayName[index]`

*Iterating through an array*

1 - Use a traditional for loop
```java

for(int i = 0; i < intArray.length; i++) {
    // loop goes here
    }
```

2 - Use a for each loop 
```java

for(int value : intArray) {
    // loop actions
    }   
```

Can't access indexes, this only gets the values. Use a normal for loop if indexes are needed. 

**Arrays of arrays**

```java

char[][] arrayArray = new char [3][];

arrayArray[sub array][sub array index] = 'value';
```

`[3]` is how many arrays is inside the array, or the grid size

## Java Records

**Data Objects** 

Created to carry data between parts of a program. These hold pieces of data and that is the primary function. 
These are passed as parameters into Services and Domain Models. 

**What problem do java records solve?**

Data object class declarations can still end up being super long code. Most of this code is called `boilerplate` which
is repetitive and easy to get wrong. Records make it so you don't have to make a massive class, and are a compact way
to declare immutable data objects

Create new java file but select record instead of class. 

WHen using a record, the parameers in the delcaration are called `record components`. These would be the data that
you are trying to put into the record to carry over. 

Records contain getters, constructors

Can add methods in the body of records. 

**What do you get for free for records?** Generated for you by Java compiler automatically when you create a record

- Immutable fields
- constructor: initializes record components passed in to the declaration
- getters: the component names so they can be accessed and returned
- equals(): compares the values of components, RECORDS USE VALUE-BASED EQUALITY
- hashCode(): uses all fields/components values when it is generated
- toString(): uses all fields/component values

**Can records have methods other than constructor, getters, equals ect...?**

Yes, but should be data helpers/manipulation rather than behavior. Classes are better for behavioral methods. 
- derived values
- formatting what is returned in a different way
- updating what is returned

**When are records a good fit?**
- when class is mostly data
- when you want immutability, read only
- when equality should be based on the values of the fields

**When are records not a good fit?**
- if object has a lot of mutable states
- Complex cycle or behavior heavy logic
- Has a unique identity that shouldn't be based on all its fields


## Exceptions & Exception Handling

**What are exceptions?**
- abnormal conditions that can occur in a Java class
- Allows you to separate normal processing logic from abnormal logic
- Represented by classes and objects in Java

**How to use try/catch blocks**

Syntax

```java
try {
    // code that may throw an exceotion
} catch(someExceptionType ex) {
    // code to handle the exception
} catch(otherExceptionType ex) {
    // code to handle exception
}
```

Multi-catch, separate the exception types in the catch declaration with `||`

Can catch specific exception types or catch all exceptions. First catch block can be a very specific one likely to 
occur and the following can be more general. 


**Checked vs unchecked**

Checked exceptions: 
- non runtim exceptions 
- use the Handle or Declare rule 

**Handle an exception**
- Try catch block

**Throw an exception**
- Anything throwed needs to be inherited from the class or object
- Throw the exception frmo the class

**Finally blocks**
- always executes

**Custom exception classes**







## Java Classes

Constructor, variables (static or private), getters - gets the value of a variable and returns it 

`Constructor`

`equals()` 

`hashCode()` 

`toString()`

`private` -

`final` - variable cannot be changed

`@Override`

Getters - 













## Programming Exam

Take practice exam on canvas - it is an exact copy of what needs to be uploaded

Can have Javadoc, chess phase-0 specification, and the Game of Chess Github page open


### BISHOP
- up & right: row+1, col+1
- up & left: row+1, col-1
- down & right: row-1, col+1
- down & left: row-1, col-1

  | Direction  | Row change | Col change | Boundary check   |
  |------------|------------|------------|------------------|
  | Up-right   | +1         | +1         | > 8 or > 8  done | 
  | Up-left    | +1         | -1         | > 8 or < 1  done |
  | Down-right | -1         | +1         | < 1 or > 8  done |
  | Down-left  | -1         | -1         | < 1 or < 1  done |

### ROOK


| Direction | Row change | Col change | Boundary check |
|-----------|------------|------------|----------------|
| Forward   | +1         | 0          | row > 8        | 
| Backwards | -1         | 0          | row < 1        |
| Right     | 0          | +1         | col > 8        |
| Left      | 0          | -1         | col < 1        |


### QUEEN

Combine Bishop and Rook moving logic


### KING

Same move logic as Queen but has to stop after only one square

### KNIGHT

+2 -1 done

+2 +1 done

+1 -2 done

+1 +2 done

-1 -2 done

-1 +2 done

-2 +1 done

-2 -1 

### PAWN

## MAIN TESTS

[] ChessBoardTests

[x] ChessMoveTests

[x] ChessPieceTests

[x] ChessPositionTests

[] EqualsTestingUtility


## PIECE MOVES TESTS

[x] BishopMoveTests

[x] KingMoveTests

[x] KnightMoveTests

[x] PawnMoveTests

[x] QueenMoveTests

[x] RookMoveTests
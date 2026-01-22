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

*Javadoc*
- Documentation for Java class library 
    - Download: Java 25 Api
- `/**` is a multiline comment, that tells Javadoc to read all the lines
- First sentence is the summary, following lines are additional details
- `*/.` the additional comments under the first main comment
- Can comment variables and methods
- Have Javadoc page open while coding / the exam

### Primitive Data Types

There are 8 types

*int*
- regular non decimal integers
- has a max number that can be stored in int type with Java
- default number size for integers

*long*
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

*Arrays of arrays*

```java

char[][] arrayArray = new char [3][];

arrayArray[sub array][sub array index] = 'value';
```
`[3]` is how many arrays is inside the array, or the grid size


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

### PAWN

## MAIN TESTS

[] ChessBoardTests

[x] ChessMoveTests

[x] ChessPieceTests

[x] ChessPositionTests

[] EqualsTestingUtility


## PIECE MOVES TESTS

[x] BishopMoveTests

[] KingMoveTests

[] KnightMoveTests

[] PawnMoveTests

[x] QueenMoveTests

[x] RookMoveTests
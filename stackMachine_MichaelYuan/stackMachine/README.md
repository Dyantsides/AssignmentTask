# Stack Machine

A simple stack machine

## Usage

Make sure you have java 11, then just run

java -jar $PROJECT_FOLDER/stackMachine.jar

## Valid Instructions

- PUSH \<xyz> or \<xyz>
  
    where \<xyz> is a valid decimal number
  
    Pushes the numeric value \<xyz> to the top of the stack
- POP
  
    Removes the top element from the stack
- CLEAR
  
    Removes all elements from the stack
- ADD

    Adds the top 2 elements on the stack and pushes the result back to the stack
- MUL

    Multiplies the top 2 elements on the stack and pushes the result back to the stack
- NEG

    Negates the top element on the stack and pushes the result back to the stack
- INV

    Inverts the top element on the stack and pushes the result back to the stack
- UNDO

    The last instruction is undone leaving the stack in the same state as before that instruction
- PRINT

    Prints all elements that are currently on the stack
- QUIT

    Exits the program
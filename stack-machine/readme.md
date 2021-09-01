## **Operation manual**

  ### Pre-requsites

  * Maven
  * JDK14

  ### **Start up application**

  The application can be started directly by running the following command at the project root folder

    mvn spring-boot:run

  If you would like to create a JAR file from the source, run the following command

    mvn clean package

  A JAR file will be created in the "target" folder and can be executed by the following command

    java -jar stack-machine-<artifact version>.jar

  ### **Command guide**
  
  #### **PUSH command**
  
  Pushes the numeric value <xyz> to the top of the stack.

  Format:
  
    PUSH <xyz> or <xyz>

  #### **POP command**
  
  Removes the top element from the stack.

  If there are no more elements in the stack, system will print "There are now no elements in the stack".

  Format:
  
    POP

  #### **CLEAR command**
  
  Removes all elements from the stack.

  After executing the command, system will print "There are now no elements in the stack".

  Format:
  
    CLEAR

  #### **ADD command**
  
  Adds the top 2 elements on the stack and pushes the result back to the stack.

  Format:
  
    ADD

  #### **MUL command**
  
  Multiplies the top 2 elements on the stack and pushes the result back to the stack.

  Format:
  
    MUL

  #### **NEG command**
  
  Negates the top element on the stack and pushes the result back to the stack.

  Format:
  
    NEG

  #### **INV command**
  
  Inverts the top element on the stack and pushes the result back to the stack.

  Format:
  
    INV

  #### **UNDO command**
  
  If the previous command is one of the "contaminating" operations, i.e. "PUSH/POP/CLEAR/ADD/MUL/NEG/INV", the last instruction is undone leaving the stack in the same state as before that instruction.

  If there are no commands having executed yet, executing UNDO will have no effect, i.e. the stack will still be empty.

  Format:
  
    UNDO

  #### **PRINT command**
  
  Prints all elements that are currently on the stack, in the order of top element to bottom element.

  If there are no elements in the stack, system will print "There are now no elements in the stack".

  Format:
  
    PRINT

  #### **QUIT command**
  
  Exits the program.

  Format:
  
    QUIT
  
<br/>
All the displayed values will be rounded off to 2 decimal places.

<br/>
<br/>
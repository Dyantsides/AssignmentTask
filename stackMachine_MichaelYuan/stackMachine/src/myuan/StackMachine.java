package myuan;

import java.util.*;
import java.util.stream.IntStream;

public class StackMachine {
    private static final Deque<Double> stack = new ArrayDeque();
    private static final Deque<Double> undoValues = new ArrayDeque();
    private static final Deque<Operation> undoOperations = new ArrayDeque();
    private static Scanner scanner = new Scanner(System.in);
    private static String ERROR_EMPTY = "Error: The stack is empty";
    private static String ERROR_NOT_ENOUGH = "Error: Less than 2 elements for ";
    private static String ERROR_INVALID = "Error: Invalid instruction";

    public static void main(String[] args) {
        StackMachine stackMachine = new StackMachine();
        stackMachine.ask();
    }

    private void ask() {
       print("Please type your instruction:");
       String[] inputList = scanner.nextLine().trim().split("\\s+");
       if (inputList.length == 1) {
           process(inputList[0]);
           return;
       } else if (inputList.length == 2) {
           Operation operation = Operation.get(inputList[0]);
           if (Operation.PUSH == operation) {
               processPush(inputList[1]);
               return;
           }
       }
       printErrorAndAsk(ERROR_INVALID);
    }

    private void process(String instruction) {
        Operation operation = Operation.get(instruction);
        switch (operation) {
            case POP:
                processPop();
                break;
            case CLEAR:
                processClear();
                break;
            case ADD:
                processAdd();
                break;
            case MUL:
                processMul();
                break;
            case NEG:
                processNeg();
                break;
            case INV:
                processInv();
                break;
            case PRINT:
                processPrint();
                break;
            case QUIT:
                processQuit();
                break;
            case UNDO:
                processUndo();
                break;
            default:
                processPush(instruction);
        }
    }

    private void processUndo() {
        try {
            Operation lastOperation = undoOperations.pop();
            switch (lastOperation) {
                case PUSH:
                  stack.pop();
                  break;
                case POP:
                  stack.push(undoValues.pop());
                  break;
                case ADD:
                case MUL:
                  stack.pop();
                  stack.push(undoValues.pop());
                  stack.push(undoValues.pop());
                  break;
                case INV:
                case NEG:
                   stack.pop();
                   stack.push(undoValues.pop());
                   break;
                case CLEAR:
                  int length = undoValues.pop().intValue();
                  IntStream.range(0, length).forEach(i -> stack.push(undoValues.pop()));
                  break;
            }
        } catch (NoSuchElementException ex) {
            ;
        }
        printTopAndAsk();
    }

    private void processQuit() {
        scanner.close();
        print("Bye bye");
        System.exit(0);
    }

    private void processPrint() {
        print(Arrays.toString(stack.toArray()));
        ask();
    }

    private void processInv() {
        if (stack.isEmpty()) {
            printErrorAndAsk(ERROR_EMPTY);
            return;
        }
        undoOperations.push(Operation.INV);
        var top = stack.pop();
        undoValues.push(top);
        stack.push(1.0 / top);
        printTopAndAsk();
    }

    private void processNeg() {
        if (stack.isEmpty()) {
            printErrorAndAsk(ERROR_EMPTY);
            return;
        }
        undoOperations.push(Operation.NEG);
        var top = stack.pop();
        undoValues.push(top);
        stack.push(top * -1.0);
        printTopAndAsk();
    }

    private void processAdd() {
        if (stack.size() < 2) {
            printErrorAndAsk(ERROR_NOT_ENOUGH + Operation.ADD);
            return;
        }
        undoOperations.push(Operation.ADD);
        var e1 = stack.pop();
        var e2 = stack.pop();
        undoValues.push(e1);
        undoValues.push(e2);
        stack.push(e1 + e2);
        printTopAndAsk();
    }

    private void processMul() {
        if (stack.size() < 2) {
            printErrorAndAsk(ERROR_NOT_ENOUGH + Operation.MUL);
            return;
        }
        undoOperations.push(Operation.MUL);
        var e1 = stack.pop();
        var e2 = stack.pop();
        undoValues.push(e1);
        undoValues.push(e2);
        stack.push(e1 * e2);
        printTopAndAsk();
    }

    private void processClear() {
        undoOperations.push(Operation.CLEAR);
        int lengthBeforeClear = stack.size();
        while (!stack.isEmpty()) {
            undoValues.push(stack.pop());
        }
        undoValues.push(lengthBeforeClear * 1.0);
        printTopAndAsk();
    }

    private void processPop() {
        try {
            undoOperations.push(Operation.POP);
            undoValues.push(stack.pop());
            printTopAndAsk();
        } catch (NoSuchElementException ex) {
            printErrorAndAsk(ERROR_EMPTY);
        }
    }

    private void processPush(String text) {
        Double value = getDoubleValue(text);
        if (value == null) {
            printErrorAndAsk(ERROR_INVALID);
            return;
        }
        undoOperations.push(Operation.PUSH);
        stack.push(value);
        printTopAndAsk();
    }

    private void printTopAndAsk() {
        Double top = stack.peek();
        print(top == null ? "" : top);
        ask();
    }

    private void printErrorAndAsk(String error) {
        print(error + ". Instruction ignored.");
        ask();
    }

    private void print(Object object) {
        System.out.println(object);
    }

    private Double getDoubleValue(String str) {
        try {
            double value = Double.parseDouble(str);
            return Double.isFinite(value) ? value : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

}

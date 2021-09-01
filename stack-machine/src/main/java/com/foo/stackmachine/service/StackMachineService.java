package com.foo.stackmachine.service;

import com.foo.stackmachine.config.ErrorMessageProperties;
import com.foo.stackmachine.model.StackMachine;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Stack;

import static com.foo.stackmachine.util.AppConstant.EMPTY_STRING;
import static com.foo.stackmachine.util.AppConstant.ZERO_DECIMAL_FORMAT;
import static java.lang.Double.valueOf;

@Service
public class StackMachineService {

    private ValidationService validationService;

    private StackMachine stackMachine;

    private ErrorMessageProperties errorMessageProperties;
    //todo how about there are millions even billions of instructions? will it raise memory issues?
    //maybe we need to use LinkedList instead?! just like what Hashmap does in JDK7 of handling collision?

    private Stack<Double> currentStack;
    private Stack<Double> backupStack;

    public StackMachineService(
            final ValidationService validationService,
            final StackMachine stackMachine,
            final ErrorMessageProperties errorMessageProperties) {
        this.validationService = validationService;
        this.stackMachine = stackMachine;
        this.errorMessageProperties = errorMessageProperties;
    }

    @PostConstruct
    public void init() {
        currentStack = stackMachine.getCurrentStack();
        backupStack = stackMachine.getBackupStack();
    }

    public Double push(final String paramStr) throws IllegalArgumentException {
        validationService.validatePush(paramStr);

        backup();

        currentStack.push(valueOf(paramStr));
        return currentStack.peek();
    }

    public String pop() throws IllegalArgumentException {

        validationService.validatePop();
        backup();
        currentStack.pop();

        if (currentStack.size() > 0) {
            return ZERO_DECIMAL_FORMAT.format(currentStack.peek());
        }

        return EMPTY_STRING;
    }

    public void clear() {

        backup();
        currentStack.removeAllElements();
    }

    public Double add() throws IllegalArgumentException {

        validationService.validateAdd();

        backup();

        final Double firstNum = currentStack.pop();
        final Double secondNum = currentStack.pop();

        return currentStack.push(firstNum + secondNum);
    }

    public Double mul() throws IllegalArgumentException {

        validationService.validateMul();

        backup();

        final Double firstNum = currentStack.pop();
        final Double secondNum = currentStack.pop();

        return currentStack.push(firstNum * secondNum);
    }

    public Double neg() throws IllegalArgumentException {

        validationService.validateNeg();

        backup();

        final Double num = currentStack.pop();

        return currentStack.push(-1 * num);
    }

    public Double inv() throws IllegalArgumentException {

        validationService.validateInv();

        backup();

        final Double num = currentStack.pop();

        return currentStack.push(1 / num);
    }

    public String undo() {

        // Replace currentStack elements with that of backupStack
        currentStack.removeAllElements();
        currentStack.addAll(backupStack);

        if (currentStack.size() > 0) {
            return ZERO_DECIMAL_FORMAT.format(currentStack.peek());
        }

        return EMPTY_STRING;
    }

    public String print() {

        if (currentStack.isEmpty()) {
            return errorMessageProperties.getStackIsEmpty();
        } else {
            String printStr = "";

            for (final Double num : currentStack) {
                printStr = ZERO_DECIMAL_FORMAT.format(num).concat(",").concat(printStr);
            }

            printStr = printStr.substring(0, printStr.length() - 1);

            return printStr;
        }
    }

    protected void backup() {
        backupStack.removeAllElements();
        backupStack.addAll(currentStack);
    }

    //for test purpose ONLY
    protected Stack<Double> getCurrentStack() {
        return currentStack;
    }

}

package com.foo.stackmachine.service;

import com.foo.stackmachine.config.ErrorMessageProperties;
import com.foo.stackmachine.model.StackMachine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Stack;

import static com.foo.stackmachine.model.Command.ADD;
import static com.foo.stackmachine.model.Command.CLEAR;
import static com.foo.stackmachine.model.Command.INV;
import static com.foo.stackmachine.model.Command.MUL;
import static com.foo.stackmachine.model.Command.NEG;
import static com.foo.stackmachine.model.Command.POP;
import static com.foo.stackmachine.model.Command.PRINT;
import static com.foo.stackmachine.model.Command.PUSH;
import static com.foo.stackmachine.model.Command.QUIT;
import static com.foo.stackmachine.model.Command.UNDO;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Service
@Slf4j
public class ValidationService {

    private StackMachine stackMachine;

    private ErrorMessageProperties errorMessageProperties;

    private Stack<Double> currentStack;

    public ValidationService(
            final StackMachine stackMachine,
            final ErrorMessageProperties errorMessageProperties) {
        this.stackMachine = stackMachine;
        this.errorMessageProperties = errorMessageProperties;
    }

    @PostConstruct
    public void init() {
        currentStack = stackMachine.getCurrentStack();
    }

    public void validateUserInput(final String userInput) throws IllegalArgumentException {
        if (StringUtils.isEmpty(userInput)) {

            final String inputIsEmpty = errorMessageProperties.getInputIsEmpty();
            log.error(inputIsEmpty);
            throw new IllegalArgumentException(inputIsEmpty);
        }

        final String[] userInputArr = userInput.split(" ");

        final String command = userInputArr[0].toUpperCase();

        if (equalsIgnoreCase(command, PUSH.name())) {
            validatePushUserInput(userInputArr);
        } else if (isValidCommand(command)) {
            //make sure the size of command is ONE
            validateNonPushUserInput(userInputArr);
        } else {
            // It is an invalid command
            final String errorInvalidInput = errorMessageProperties.getInvalidInput();
            log.error(errorInvalidInput);
            throw new IllegalArgumentException(errorInvalidInput);
        }
    }

    protected boolean isValidCommand(final String command) {
        return equalsIgnoreCase(command, POP.name()) || equalsIgnoreCase(command, CLEAR.name())
                || equalsIgnoreCase(command, ADD.name()) || equalsIgnoreCase(command, MUL.name())
                || equalsIgnoreCase(command, NEG.name()) || equalsIgnoreCase(command, INV.name())
                || equalsIgnoreCase(command, UNDO.name()) || equalsIgnoreCase(command, PRINT.name())
                || equalsIgnoreCase(command, QUIT.name());
    }

    protected void validatePushUserInput(final String[] userInputArr) throws IllegalArgumentException {
        if (!(userInputArr.length == 2)) {
            // Invalid "PUSH" input command
            final String errorInvalidInput = errorMessageProperties.getInvalidInput();
            log.error(errorInvalidInput);
            throw new IllegalArgumentException(errorInvalidInput);
        }

        this.validatePush(userInputArr[1]);
    }

    protected void validateNonPushUserInput(final String[] userInputArr) throws IllegalArgumentException {
        //if it is not a push command
        if (!(userInputArr.length == 1)) {
            final String errorInvalidInput = errorMessageProperties.getInvalidInput();
            log.error(errorInvalidInput);
            throw new IllegalArgumentException(errorInvalidInput);
        }
    }

    public void validatePush(final String paramStr) throws IllegalArgumentException {
        try {
            Double.valueOf(paramStr);
        } catch (NumberFormatException exception) {
            // Param not a decimal number
            final String errorParamNotDecimalNum
                    = errorMessageProperties.getParamNotDecimalNum();
            log.error(errorParamNotDecimalNum);
            throw new IllegalArgumentException(errorParamNotDecimalNum);
        }
    }

    public void validatePop() throws IllegalArgumentException {
        if (currentStack.isEmpty()) {
            final String errorStackIsEmpty = errorMessageProperties.getStackIsEmpty();

            log.error(errorStackIsEmpty);
            throw new IllegalArgumentException(errorStackIsEmpty);
        }
    }

    public void validateAdd() throws IllegalArgumentException {
        //If there are not enough elements on the stack for a particular instruction, an error is logged and the
        //instruction is ignored. The user is prompted for the next instruction.
        if (!(currentStack.size() >= 2)) {
            final String errorNotEnoughElementsToAdd
                    = errorMessageProperties.getNotEnoughElementsToAdd();
            log.error(errorNotEnoughElementsToAdd);
            throw new IllegalArgumentException(errorNotEnoughElementsToAdd);
        }
    }

    public void validateMul() throws IllegalArgumentException {
        if (!(currentStack.size() >= 2)) {
            final String errorNotEnoughElementsToMul
                    = errorMessageProperties.getNotEnoughElementsToMul();
            log.error(errorNotEnoughElementsToMul);
            throw new IllegalArgumentException(errorNotEnoughElementsToMul);
        }
    }

    public void validateNeg() throws IllegalArgumentException {
        if (currentStack.isEmpty()) {
            final String errorStackIsEmpty = errorMessageProperties.getStackIsEmpty();
            log.error(errorStackIsEmpty);
            throw new IllegalArgumentException(errorStackIsEmpty);
        }
    }

    public void validateInv() throws IllegalArgumentException {
        if (currentStack.isEmpty()) {
            final String errorStackIsEmpty = errorMessageProperties.getStackIsEmpty();
            log.error(errorStackIsEmpty);
            throw new IllegalArgumentException(errorStackIsEmpty);
        }

        final Double num = currentStack.peek();

        if (num.equals(0.00D)) {
            final String errorZeroCannotBeInverted
                    = errorMessageProperties.getZeroCannotBeInverted();
            log.error(errorZeroCannotBeInverted);
            throw new IllegalArgumentException(errorZeroCannotBeInverted);
        }
    }
}

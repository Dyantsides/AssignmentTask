package com.foo.stackmachine.service;


import com.foo.stackmachine.config.ErrorMessageProperties;
import com.foo.stackmachine.model.StackMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Stack;

import static com.foo.stackmachine.model.Command.ADD;
import static com.foo.stackmachine.model.Command.CLEAR;
import static com.foo.stackmachine.model.Command.INV;
import static com.foo.stackmachine.model.Command.MUL;
import static com.foo.stackmachine.model.Command.NEG;
import static com.foo.stackmachine.model.Command.POP;
import static com.foo.stackmachine.model.Command.PRINT;
import static com.foo.stackmachine.model.Command.QUIT;
import static com.foo.stackmachine.model.Command.UNDO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {
    private ValidationService validationService;

    @Mock
    private ErrorMessageProperties errorMessageProperties;

    @Mock
    private StackMachine stackMachine;

    @BeforeEach
    public void setup() {
        validationService = new ValidationService(stackMachine, errorMessageProperties);
    }

    @Test
    @DisplayName("If ValidCommand then True")
    public void isValidCommand_shouldReturnTrueWhenValidCommandProvided() {
        final String[] commands = {POP.name(), CLEAR.name(), ADD.name(), MUL.name(), NEG.name(), INV.name(), UNDO.name(), PRINT.name(), QUIT.name()};
        for (String incomingCommand : commands) {
            boolean isValidCommand = validationService.isValidCommand(incomingCommand);
            System.out.println(incomingCommand);
            assertTrue(isValidCommand);
        }
    }

    @Test
    @DisplayName("If InValidCommand then False")
    public void isValidCommand_shouldReturnFalseWhenInValidCommandProvided() {
        final String command = "Invalid Command";
        boolean isValidCommand = validationService.isValidCommand(command);
        assertFalse(isValidCommand);
    }

    @Test
    public void validatePushUserInput_happy_path() {
        final String[] userInputArr = {"PUSH", "1.5"};
        validationService.validatePushUserInput(userInputArr);
    }

    @Test
    public void validatePushUserInput_raiseExceptionWhenInputNumberIncorrect() {
        when(errorMessageProperties.getInvalidInput()).thenReturn("InvalidInput");

        final String[] userInputArr = {"PUSH"};

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validatePushUserInput(userInputArr));
    }

    @Test
    public void validateNonPushUserInput_raiseExceptionWhenInputNumberIncorrect() {
        when(errorMessageProperties.getInvalidInput()).thenReturn("InvalidInput");

        final String[] userInputArr = {"ADD", "1.5"};

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateNonPushUserInput(userInputArr));
    }

    @Test
    public void validatePushUserInput_raiseExceptionWhen2ndParamNotNumber() {

        final String[] userInputArr = {"PUSH", "hello"};

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validatePushUserInput(userInputArr));
    }

    @Test
    public void validateUserInput_raiseExceptionWhenUserInputIsEmpty() {
        final String[] userInputArr = {"", null};
        for (String userInput : userInputArr) {

            assertThrows(
                    IllegalArgumentException.class,
                    () -> validationService.validateUserInput(userInput));
        }
    }

    @Test
    public void validatePush_happy_path() {
        validationService.validatePush("1.5");

        verify(errorMessageProperties, times(0)).getParamNotDecimalNum();

    }

    @Test
    public void validatePush_shouldRaiseExceptionWhenNotANumber() {
        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateUserInput("hello"));

    }

    @Test
    public void validatePop_happy_path(){
        final Stack<Double> currentStack = new Stack<>();
        currentStack.push(1.3);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        validationService.validatePop();

        verify(errorMessageProperties, times(0)).getStackIsEmpty();
    }

    @Test
    public void validatePop_shouldRaiseExceptionWhenStackIsEmpty(){
        final Stack<Double> currentStack = new Stack<>();

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validatePop());
    }

    @Test
    public void validateNeg_happy_path(){
        final Stack<Double> currentStack = new Stack<>();
        currentStack.push(1.3);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        validationService.validateNeg();

        verify(errorMessageProperties, times(0)).getStackIsEmpty();
    }

    @Test
    public void validateNeg_shouldRaiseExceptionWhenStackIsEmpty(){
        final Stack<Double> currentStack = new Stack<>();

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateNeg());
    }

    @Test
    public void validateAdd_happy_path(){
        final Stack<Double> currentStack = new Stack<>();
        currentStack.push(1.3);
        currentStack.push(1.4);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        validationService.validateAdd();

        verify(errorMessageProperties, times(0)).getNotEnoughElementsToAdd();
    }

    @Test
    public void validateAdd_shouldRaiseExceptionWhenStackSizeLessThan2(){
        final Stack<Double> currentStack = new Stack<>();
        currentStack.push(1.3);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateAdd());

    }

    @Test
    public void validateAdd_shouldRaiseExceptionWhenStackIsEmpty(){
        final Stack<Double> currentStack = new Stack<>();

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateAdd());

    }

    @Test
    public void validateMul_happy_path(){
        final Stack<Double> currentStack = new Stack<>();
        currentStack.push(1.3);
        currentStack.push(1.4);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        validationService.validateMul();

        verify(errorMessageProperties, times(0)).getNotEnoughElementsToMul();
    }

    @Test
    public void validateMul_shouldRaiseExceptionWhenStackSizeLessThan2(){
        final Stack<Double> currentStack = new Stack<>();
        currentStack.push(1.3);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateMul());

    }

    @Test
    public void validateMul_shouldRaiseExceptionWhenStackIsEmpty(){
        final Stack<Double> currentStack = new Stack<>();

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateMul());
    }


    @Test
    public void validateInv_happy_path(){
        final Stack<Double> currentStack = new Stack<>();
        currentStack.push(1.3);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        validationService.validateInv();

        verify(errorMessageProperties, times(0)).getStackIsEmpty();
    }

    @Test
    public void validateInv_shouldRaiseExceptionWhenStackIsEmpty(){
        final Stack<Double> currentStack = new Stack<>();

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateInv());
    }

    @Test
    public void validateInv_shouldRaiseExceptionWhenNumberIsZero(){
        final Stack<Double> currentStack = new Stack<>();
        //it really does not matter whether it is 0.0D or 0.00D
        currentStack.push(0.000D);

        when(stackMachine.getCurrentStack()).thenReturn(currentStack);

        validationService = new ValidationService(stackMachine, errorMessageProperties);

        validationService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateInv());
    }
}
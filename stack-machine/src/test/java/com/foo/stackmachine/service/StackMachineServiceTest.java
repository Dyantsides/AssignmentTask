package com.foo.stackmachine.service;

import com.foo.stackmachine.config.ErrorMessageProperties;
import com.foo.stackmachine.model.StackMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Stack;

import static com.foo.stackmachine.util.AppConstant.EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StackMachineServiceTest {
    private StackMachineService stackMachineService;

    @Mock
    private ValidationService validationService;

    @Mock
    private ErrorMessageProperties errorMessageProperties;

    private StackMachine stackMachine;

    @BeforeEach
    public void setup() {
        stackMachine = new StackMachine();
        Stack<Double> currentStack = new Stack<>();

        Stack<Double> backupStack = new Stack<>();
        stackMachine.setBackupStack(backupStack);
        stackMachine.setCurrentStack(currentStack);

        stackMachineService =
                new StackMachineService(
                        validationService,
                        stackMachine,
                        errorMessageProperties);
    }

    @Test
    public void push_happy_path() {
        stackMachineService.init();

        final Double pushedValue = stackMachineService.push("1.5");

        assertNotNull(pushedValue);
        assertEquals("1.5", pushedValue.toString());
    }

    @Test
    public void push_shouldRaiseExceptionWhenInputStringNotANumber() {
        stackMachineService.init();

        assertThrows(
                IllegalArgumentException.class,
                () -> stackMachineService.push("hello"));

    }

    @Test
    public void pop_happy_path() {
        stackMachineService.init();

        stackMachineService.push("1.5");
        stackMachineService.push("1.6");
        final String popedValue = stackMachineService.pop();

        assertAll(
                () -> assertFalse(StringUtils.isBlank(popedValue)),
                () -> assertEquals("1.50", popedValue)
        );
    }

    @Test
    public void pop_shouldReturnEmptyWhenStackHasOneItem() {
        stackMachineService.init();

        stackMachineService.push("1.5");
        final String popedValue = stackMachineService.pop();

        assertAll(
                () -> assertNotNull(popedValue),
                () -> assertEquals(EMPTY_STRING, popedValue)
        );
    }

    @Test
    public void clear_happy_path() {
        stackMachineService.init();
        stackMachineService.push("1.5");

        final Stack<Double> currentStack_before = stackMachineService.getCurrentStack();
        assertFalse(currentStack_before.empty());

        stackMachineService.clear();
        final Stack<Double> currentStack = stackMachineService.getCurrentStack();
        assertTrue(currentStack.empty());
    }

    @Test
    public void add_happy_path() {
        stackMachineService.init();
        stackMachineService.push("1.5");
        stackMachineService.push("1.6");

        final Double pop = stackMachineService.add();

        assertEquals(3.1, pop);
    }

    @Test
    public void neg_happy_path() {
        stackMachineService.init();
        stackMachineService.push("1.5");

        final Double neg = stackMachineService.neg();

        assertEquals(-1.5, neg);
    }

    @Test
    public void inv_happy_path() {
        stackMachineService.init();
        stackMachineService.push("2.0");

        final Double inv = stackMachineService.inv();

        assertEquals(0.5, inv);
    }

    @Test
    public void undo_happy_path() {
        stackMachineService.init();
        stackMachineService.push("1.5");
        stackMachineService.push("2.0");

        final Stack<Double> currentStack = stackMachineService.getCurrentStack();
        assertEquals(2, currentStack.size());
        final String undo = stackMachineService.undo();

        assertEquals("1.50", undo);
        assertEquals(1, currentStack.size());
    }

    @Test
    public void mul_happy_path() {
        stackMachineService.init();
        stackMachineService.push("1.5");
        stackMachineService.push("1.5");

        final Double pop = stackMachineService.mul();

        assertEquals(2.25, pop);
    }

    @Test
    public void print_happy_path() {
        stackMachineService.init();
        stackMachineService.push("1.5");
        stackMachineService.push("1.6");

        final String actualPrintString = stackMachineService.print();
        final String expectedPrintString = "1.60,1.50";

        assertEquals(expectedPrintString, actualPrintString);
    }

    @Test
    public void print_stackIsEmpty() {
        final String expectedMessage = "Error: Stack is empty!";
        when(errorMessageProperties.getStackIsEmpty()).thenReturn("Error: Stack is empty!");

        stackMachineService.init();

        final String actualPrintString = stackMachineService.print();

        assertEquals(expectedMessage, actualPrintString);
    }
}
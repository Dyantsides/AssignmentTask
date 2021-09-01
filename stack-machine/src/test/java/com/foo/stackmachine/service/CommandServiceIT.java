package com.foo.stackmachine.service;

import com.foo.stackmachine.config.ErrorMessageProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Stack;

import static com.foo.stackmachine.util.AppConstant.ZERO_DECIMAL_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CommandServiceIT {

    private CommandService commandService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private StackMachineService stackMachineService;

    @Autowired
    private ErrorMessageProperties errorMessageProperties;

    @BeforeEach
    public void setup() {
        commandService = new CommandService(
                validationService,
                stackMachineService,
                errorMessageProperties);
        stackMachineService.getCurrentStack().clear();
    }

    @Test
    public void shouldBeAbleToHandleSimpleInstructions() {
        String[] instructions = {"PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV", "PRINT"};
        for (final String instruction : instructions) {
            commandService.execute(instruction);
        }

        final Stack<Double> currentStack = stackMachineService.getCurrentStack();

        assertNotNull(currentStack.get(0));
        assertEquals(Double.valueOf("0.2"), currentStack.get(0));
    }

    @Test
    public void shouldBeAbleToHandleComplexInstructions() {
        final String[] instructions =
                {"PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PUSH 1.5", "PUSH -3.5", "PUSH -1", "MUL", "ADD", "INV","ADD",
                        "PRINT"
                };
        for (final String instruction : instructions) {
            commandService.execute(instruction);
        }

        final Stack<Double> currentStack = stackMachineService.getCurrentStack();

        assertNotNull(currentStack.get(0));
        assertEquals(ZERO_DECIMAL_FORMAT.format(Double.valueOf("4.0")), ZERO_DECIMAL_FORMAT.format(currentStack.get(0)));
    }

    @Test
    public void shouldRaiseExceptionWhenInverseZero() {
        String[] instructions = {"PUSH 1.5", "PUSH -1.5", "ADD"};
        for (final String instruction : instructions) {
            commandService.execute(instruction);
        }

        assertThrows(
                IllegalArgumentException.class,
                ()->commandService.execute("INV")
        );

    }
}
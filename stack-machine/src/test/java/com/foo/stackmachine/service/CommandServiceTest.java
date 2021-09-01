package com.foo.stackmachine.service;

import com.foo.stackmachine.config.ErrorMessageProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandServiceTest {

    private CommandService commandService;

    @Mock
    private ValidationService validationService;
    @Mock
    private StackMachineService stackMachineService;

    @Mock
    private ErrorMessageProperties errorMessageProperties;

    @BeforeEach
    public void setup(){
        commandService = new CommandService(
                validationService,
                stackMachineService,
                errorMessageProperties);
    }

    @Test
    public void push_happy_path(){
        final String userInput = "PUSH 1.5";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).push(anyString());

    }

    @Test
    public void pop_happy_path(){
        final String userInput = "POP";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).pop();

    }

    @Test
    public void clear_happy_path(){
        final String userInput = "CLEAR";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).clear();

    }

    @Test
    public void add_happy_path(){
        final String userInput = "ADD";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).add();

    }

    @Test
    public void mul_happy_path(){
        final String userInput = "MUL";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).mul();

    }

    @Test
    public void neg_happy_path(){
        final String userInput = "NEG";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).neg();

    }

    @Test
    public void inv_happy_path(){
        final String userInput = "INV";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).inv();

    }

    @Test
    public void undo_happy_path(){
        final String userInput = "UNDO";
        commandService.execute(userInput);

        verify(stackMachineService, times(1)).undo();

    }
}
package com.foo.stackmachine.service;

import com.foo.stackmachine.config.ErrorMessageProperties;
import com.foo.stackmachine.model.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.foo.stackmachine.util.AppConstant.ZERO_DECIMAL_FORMAT;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Slf4j
@Service
public class CommandService {
    private ValidationService validationService;
    private StackMachineService stackMachineService;
    private ErrorMessageProperties errorMessageProperties;

    public CommandService(
            final ValidationService validationService,
            final StackMachineService stackMachineService,
            final ErrorMessageProperties errorMessageProperties) {
        this.validationService = validationService;
        this.stackMachineService = stackMachineService;
        this.errorMessageProperties = errorMessageProperties;
    }

    public void execute(final String userInput) throws IllegalArgumentException {
        //If the user input is invalid, an error is logged and the input is ignored. The user is prompted for the
        //next instruction.
        validationService.validateUserInput(userInput);

        //since the input string has already been validated, it is not possible to be empty
        final String[] userInputArr = userInput.split(" ");
        final String order = userInputArr[0].toUpperCase();

        //If the user input is valid, the instruction is executed and the top element of the stack is printed(here I just log.debug).
        switch (Command.valueOf(order)) {
            case PUSH:
                //todo assume the precision is 0.00
                final String pushedValue = ZERO_DECIMAL_FORMAT.format(stackMachineService.push(userInputArr[1]));
                log.debug("Top element: [{}]", pushedValue);
                break;
            case POP:
                String result = stackMachineService.pop();

                if (equalsIgnoreCase(result, "EMPTY")) {
                    System.out.println(errorMessageProperties.getStackIsEmpty());
                } else {
                    log.debug("Top element: [{}]", result);
                }
                break;
            case CLEAR:
                stackMachineService.clear();
                System.out.println("There are now no elements in the stack.");
                break;
            case ADD:
                final String addedValue = ZERO_DECIMAL_FORMAT.format(stackMachineService.add());
                log.debug("Top element: [{}]", addedValue);

                break;
            case MUL:
                final String mulValue = ZERO_DECIMAL_FORMAT.format(stackMachineService.mul());
                log.debug("Top element: [{}]", mulValue);
                break;
            case NEG:
                final String negValue = ZERO_DECIMAL_FORMAT.format(stackMachineService.neg());
                log.debug("Top element: [{}]", negValue);
                break;
            case INV:

                final String invValue = ZERO_DECIMAL_FORMAT.format(stackMachineService.inv());
                log.debug("Top element: [{}]", invValue);
                break;
            case UNDO:
                result = stackMachineService.undo();
                if (equalsIgnoreCase(result, "EMPTY")) {
                    System.out.println("There are now no elements in the stack.");
                } else {

                    log.debug("Top element: [{}]", result);
                }
                break;
            case PRINT:
                System.out.println(stackMachineService.print());
                break;
            case QUIT:
                System.out.println("Bye!");
                //todo how to quit a SpringBoot app nicely?
                System.exit(-1);
            default:
                System.out.println("no match");
        }
    }
}

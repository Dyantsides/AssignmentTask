package com.foo.stackmachine.model;

import java.util.Stack;

import lombok.Data;

@Data
public class StackMachine {

    private Stack<Double> currentStack;

    // For backup purpose for "UNDO" scenario
    private Stack<Double> backupStack;
}

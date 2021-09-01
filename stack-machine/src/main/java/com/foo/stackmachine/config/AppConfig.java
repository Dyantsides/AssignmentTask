package com.foo.stackmachine.config;

import com.foo.stackmachine.model.StackMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Stack;

@Configuration
public class AppConfig {
    @Bean
    public StackMachine getStackMachine() {
        StackMachine stackMachine = new StackMachine();
        stackMachine.setCurrentStack(new Stack<>());
        stackMachine.setBackupStack(new Stack<>());
        return stackMachine;
    }

}

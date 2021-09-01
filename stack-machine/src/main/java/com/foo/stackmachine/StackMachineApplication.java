package com.foo.stackmachine;

import com.foo.stackmachine.config.ErrorMessageProperties;
import com.foo.stackmachine.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@Slf4j
public class StackMachineApplication implements CommandLineRunner {

    @Autowired
    private CommandService commandService;

    @Autowired
    private ErrorMessageProperties errorMessageProperties;

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(StackMachineApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //test profile is for IT test
        if (env.getActiveProfiles().length == 0
                || !env.getActiveProfiles()[0].equals("test")) {

            // Instruct user to perform input
            System.out.println("==============================");
            System.out.println("Welcome to stack machine application!");
            System.out.println("Below are the possible operations:");
            System.out.println("PUSH <xyz> or <xyz>");
            System.out.println("POP");
            System.out.println("CLEAR");
            System.out.println("ADD");
            System.out.println("MUL");
            System.out.println("NEG");
            System.out.println("INV");
            System.out.println("UNDO");
            System.out.println("PRINT");
            System.out.println("QUIT");
            System.out.println("==============================");

            while (true) {
                System.out.println("Please enter your command:");

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String userInput = br.readLine();

                    commandService.execute(userInput);

                } catch (IOException exception) {
                    log.error(errorMessageProperties.getIoException());
                } catch (IllegalArgumentException e) {
                    log.error(errorMessageProperties.getInvalidCommand());
                }
            }
        }
    }
}

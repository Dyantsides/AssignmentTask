package com.foo.stackmachine.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("error")
@Data
public class ErrorMessageProperties {
    private String ioException;
    private String inputIsEmpty;

    private String invalidInput;

    private String paramNotDecimalNum;

    private String stackIsEmpty;

    private String notEnoughElementsToAdd;

    private String notEnoughElementsToMul;

    private String zeroCannotBeInverted;

    private String invalidCommand;
}

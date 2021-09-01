package com.foo.stackmachine.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MessageConfig.class})
@SpringBootTest
@ActiveProfiles("test")
class ErrorMessagePropertiesIT {
    @Autowired
    private ErrorMessageProperties errorMessageProperties;

    @Test
    public void shouldPopulateErrorMessageProperties() {
        assertThat(errorMessageProperties.getInvalidInput()).isEqualTo("Error: Invalid input!");
        assertThat(errorMessageProperties.getIoException()).isEqualTo("Error: IOException has occurred!");
        assertThat(errorMessageProperties.getInputIsEmpty()).isEqualTo("Error: Empty input not allowed!");
        assertThat(errorMessageProperties.getParamNotDecimalNum()).isEqualTo("Error: The parameter must be a decimal number!");
        assertThat(errorMessageProperties.getStackIsEmpty()).isEqualTo("Error: Stack is empty!");
        assertThat(errorMessageProperties.getNotEnoughElementsToAdd()).isEqualTo("Error: Not enough elements to perform add!");
        assertThat(errorMessageProperties.getNotEnoughElementsToMul()).isEqualTo("Error: Not enough elements to perform multiply!");
        assertThat(errorMessageProperties.getZeroCannotBeInverted()).isEqualTo("Error: 0 cannot be inverted!");
    }
}
package com.foo.stackmachine.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ErrorMessageProperties.class})
public class MessageConfig {
}

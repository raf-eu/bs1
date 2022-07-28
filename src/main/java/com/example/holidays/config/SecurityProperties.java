package com.example.holidays.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("spring.auth")
@Value
@ConstructorBinding
public class SecurityProperties {

    String userName;
    String password;

}

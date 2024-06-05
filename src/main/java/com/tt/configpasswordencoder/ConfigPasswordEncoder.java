package com.tt.configpasswordencoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigPasswordEncoder {

    @Bean
    public PasswordEncoder ConfigPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

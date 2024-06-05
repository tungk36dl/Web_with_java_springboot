package com.tt.configsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();


        httpSecurity.authorizeRequests().requestMatchers("/admin/**").authenticated();
        httpSecurity.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN");
//        httpSecurity.authorizeRequests().requestMatchers("/**").hasRole("USER");
//        httpSecurity.authorizeRequests().requestMatchers("/webjars/**").permitAll();
//

//        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity.formLogin();
        return httpSecurity.build();
    }
}

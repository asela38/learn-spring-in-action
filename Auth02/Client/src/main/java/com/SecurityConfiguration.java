package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
                .oauth2Login( login -> login.loginPage("/oauth2/authorization/tacoadmin-client"))
                .oauth2Client(Customizer.withDefaults())
                .build();
    }
}

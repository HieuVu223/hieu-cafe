package com.hieu.cafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "login").permitAll()
                        .anyRequest().hasRole("ADMIN")
                        )
                        .formLogin(form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/admin/dashboard")
                                .permitAll()
                        )
                        .logout(logout -> logout
                                .logoutSuccessUrl("/")
                                .permitAll()
                        );
        return http.build();
    }
}

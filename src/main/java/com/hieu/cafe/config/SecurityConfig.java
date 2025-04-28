package com.hieu.cafe.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//
//@Profile("!test")
//@Configuration
//@EnableWebSecurity
//
//public class SecurityConfig {
//    @Bean
//    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "login").permitAll()
//                        .anyRequest().hasRole("ADMIN")
//                        )
//                        .formLogin(form -> form
//                                .loginPage("/login")
//                                .defaultSuccessUrl("/admin/dashboard")
//                                .permitAll()
//                        )
//                        .logout(logout -> logout
//                                .logoutSuccessUrl("/")
//                                .permitAll()
//                        );
//        return http.build();
//    }
//}

//API CategoryController Testing
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Allow all requests without authentication
                )
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for Postman/testing
                .httpBasic(AbstractHttpConfigurer::disable)  // Disable HTTP Basic Auth
                .formLogin(AbstractHttpConfigurer::disable);  // Disable form login

        return http.build();
    }
}

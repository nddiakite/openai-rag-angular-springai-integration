package com.prasannjeet.social.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class BasicAuthSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
            .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
                .antMatchers("/api/users/login").permitAll()
                .antMatchers("/api/users/loggedIn").hasAnyRole("ADMIN", "MANAGER", "OPENAI")
                .antMatchers("/api/users/**").hasAnyRole("ADMIN")
                .antMatchers("/api/roles/**").hasRole("ADMIN")
                .antMatchers("/api/assistants/**").hasAnyRole("ADMIN", "OPENAI")
                .antMatchers("/api/autocomplete/**").hasAnyRole("ADMIN", "OPENAI")
                .antMatchers("/api/twitter/**").hasAnyRole("ADMIN", "OPENAI")
                .antMatchers("/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .csrf().disable()
            // Configuration pour H2 Console
            // Nécessaire pour permettre les frames pour la console H2
            .headers().frameOptions().disable();

//        PasswordEncoderUtil.main();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
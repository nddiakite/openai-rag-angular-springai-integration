package fr.dtek.lab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig {

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
//    @Autowired
//    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) ->
                 authorizationManagerRequestMatcherRegistry
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/**", HttpMethod.OPTIONS.toString())).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/users/login")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/tests/pg_vector_storage")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/users/loggedIn")).hasAnyRole("ADMIN", "MANAGER", "OPENAI")
                .requestMatchers(new AntPathRequestMatcher("/api/users/**")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/api/roles/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/api/assistants/**")).hasAnyRole("ADMIN", "OPENAI")
                .requestMatchers(new AntPathRequestMatcher("/api/autocomplete/**")).hasAnyRole("ADMIN", "OPENAI")
                .requestMatchers(new AntPathRequestMatcher("/api/twitter/**")).hasAnyRole("ADMIN", "OPENAI")
                .requestMatchers(new AntPathRequestMatcher("/api/**")).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(httpSecurityHttpBasicConfigurer ->
                    httpSecurityHttpBasicConfigurer.authenticationEntryPoint(authenticationEntryPoint))
            // Configuration pour H2 Console
            // Nécessaire pour permettre les frames pour la console H2
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

//        PasswordEncoderUtil.main();

        http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // LEGACY ENCODER
        // Useful URL : https://www.baeldung.com/spring-security-5-default-password-encoder
//        return new BCryptPasswordEncoder();
    }
}
package com.estate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/suntower",
                                "/suntower/**",
                                "/css/**",
                                "/images/**",
                                "/js/**",
                                "/login",
                                "/forgot-password",
                                "/auth/forgot-password",
                                "/auth/reset-password",
                                "/payment/**"
                        ).permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/staff/**").hasRole("STAFF")
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")

                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/login-success", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }
}

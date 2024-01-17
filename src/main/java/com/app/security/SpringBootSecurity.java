package com.app.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SpringBootSecurity {

    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests(auth -> {
                    auth
                            .requestMatchers("/administrador/**").hasRole("ADMIN")
                            .requestMatchers("/productos/**").hasRole("ADMIN")
                            .requestMatchers("/order").hasRole("USER")
                            .requestMatchers("/saveOrder").hasRole("USER")
                            .anyRequest().permitAll();
                })
                .formLogin(form -> {
                    form.loginPage("/usuario/registro").permitAll();
                    form.loginPage("/usuario/login");
                })
                .sessionManagement(sesionManager -> {
                    sesionManager.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    sesionManager.invalidSessionUrl("/usuario/login");
                    sesionManager.maximumSessions(1);
                    sesionManager.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession);
                    sesionManager.sessionAuthenticationErrorUrl("/usuario/login");
                })
                .build();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}

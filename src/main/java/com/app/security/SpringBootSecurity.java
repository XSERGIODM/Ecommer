package com.app.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

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
                    form
                            .loginPage("/usuario/login")
                            .defaultSuccessUrl("/usuario/acceder")
                            .permitAll()
                            .defaultSuccessUrl("/usuario/acceder");
                })
                .sessionManagement(sesionManager -> {
                    sesionManager
                            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                            .invalidSessionUrl("/usuario/login")
                            .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)
                            .sessionAuthenticationErrorUrl("/usuario/login")
                            .maximumSessions(1);
                })
                .build();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}

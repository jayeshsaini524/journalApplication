package net.engineeringdigest.journalApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;

@Configuration
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailService;
    
    //determines which end points to give access and which to not
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->authorizeRequests
                    .requestMatchers("/journal/**","/users**").authenticated()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()  // Allow all other requests without authentication
            )
            .httpBasic(withDefaults());  // Enable HTTP Basic Authentication
            http.csrf(csrf -> csrf.disable());

        return http.build();
    } 

    //it helps to authanticate users , Also integrate UserDetailsServiceImpl
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
            .userDetailsService(userDetailService)  // Set custom UserDetailsService
            .passwordEncoder(passwordEncoder());  // Set password encoder
        return authenticationManagerBuilder.build();
    }

    // this used for password encoding
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // For encoding passwords
    }
}
//Whats new :-
//replaced @EnableWebSecurity, extends WebSecurityConfigureAdapter -> with AuthanticationManager
//Replaced antMatchers with requestMatchers
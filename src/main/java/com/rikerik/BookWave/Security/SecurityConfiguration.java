package com.rikerik.BookWave.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * This class represents the configuration for security in the application.
 * It provides methods to configure authentication and authorization settings.
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    // Configures and returns an AuthenticationProvider
    // that uses a DaoAuthenticationProvider with a UserDetailsService
    // and a BCryptPasswordEncoder for secure password hashing.
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // Set the UserDetailsService that will be used to retrieve user details
        provider.setUserDetailsService(userDetailsService);

        // Create a new instance of BCryptPasswordEncoder
        // to securely hash and verify passwords
        provider.setPasswordEncoder(new BCryptPasswordEncoder());

        // Return the configured DaoAuthenticationProvider
        // which will be used for authentication
        return provider;
    }

    @Component
    public static class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            String username = request.getParameter("username");
            String error = exception.getMessage();
            log.info("A failed login attempt with username: "
                    + username + ". Reason: " + error);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/register", "/resources/**", "/js/**", "/css/**")
                        .permitAll()
                        .requestMatchers("/BookAddingPage").hasRole("ADMIN")
                        .anyRequest().authenticated() // the register page is available even without authentication
                )
                .formLogin((form) -> form
                        .loginPage("/login") // the only available page next to the register. If credentials are valid,
                                             // authentication is finished
                        .usernameParameter("username")
                        .failureHandler(new MyAuthenticationFailureHandler())
                        .failureUrl("/loginFailure")
                        .permitAll()
                        .defaultSuccessUrl("/", true)

                )
                .logout((logout) -> logout.logoutSuccessUrl("/login"));

        return http.build();
    }
}

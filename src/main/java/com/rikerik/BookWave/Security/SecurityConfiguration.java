package com.rikerik.BookWave.Security;

import com.rikerik.BookWave.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
// Configures and returns an AuthenticationProvider
// that uses a DaoAuthenticationProvider with a UserDetailsService
// and a BCryptPasswordEncoder for secure password hashing.
    AuthenticationProvider authenticationProvider(){
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated() //the register page is available even without authentication
                )
                .formLogin((form) -> form
                        .loginPage("/login") //the only available page next to the register. If credentials are valid, authentication is finished
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}

package com.hackathon.petstore_cms.config;

// Make sure all these imports are correct
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.hackathon.petstore_cms.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This bean tells Spring to use our UserService
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        // --- THIS IS THE FIX ---
        // We now get UserService AND PasswordEncoder as parameters
        
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); 
        authProvider.setPasswordEncoder(passwordEncoder); // <-- Now we use the parameter
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // 1. These pages are PUBLIC
                .requestMatchers(
                    "/", 
                    "/register",
                    "/login",
                    "/js/**",
                    "/style.css",
                    "/images/**"
                ).permitAll()

                // 2. These pages are ADMIN-ONLY
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // 3. All other requests must be logged in
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // We are back to using /login
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    // This bean handles where to go after login (Admin vs User)
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return (request, response, authentication) -> {
            if (authentication.getAuthorities().stream()
                      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/pets");
            } else {
                response.sendRedirect("/");
            }
        };
    }
}
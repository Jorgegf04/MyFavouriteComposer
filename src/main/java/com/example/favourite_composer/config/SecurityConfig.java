package com.example.favourite_composer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        private AuthEntryPointJwt authEntryPointJwt;

        @Bean
        public AuthTokenFilter authenticationJwtTokenFilter() {
                return new AuthTokenFilter();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {

                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder());
                return authProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
                        throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        @Order(1)
        public SecurityFilterChain apiSecurity(HttpSecurity http, DaoAuthenticationProvider authProvider)
                        throws Exception {

                http
                                .securityMatcher("/api/**")
                                .csrf(csrf -> csrf.disable())
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPointJwt))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/test/all").permitAll()
                                                .requestMatchers("/api/test/user").hasAnyRole("USER", "EDITOR", "ADMIN")
                                                .requestMatchers("/api/test/admin").hasRole("ADMIN")
                                                .anyRequest().authenticated());

                http.authenticationProvider(authProvider);

                http.addFilterBefore(authenticationJwtTokenFilter(),
                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        @Order(2)
        public SecurityFilterChain mvcSecurity(HttpSecurity http, DaoAuthenticationProvider authProvider)
                        throws Exception {

                http
                                .securityMatcher("/**")
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/search/**").permitAll()
                                                .requestMatchers("/new/**").permitAll()
                                                .requestMatchers("/login").permitAll()
                                                .requestMatchers("/show/composer", "/show/music-piece",
                                                                "/composer-pagination-list")
                                                .permitAll()
                                                .requestMatchers("/show/**").hasAnyRole("USER", "EDITOR", "ADMIN")
                                                .requestMatchers("/add/**", "/edit/**", "/delete/**")
                                                .hasAnyRole("EDITOR", "ADMIN")
                                                .requestMatchers("/users/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/signin")
                                                .loginProcessingUrl("/login")
                                                .defaultSuccessUrl("/", true)
                                                .permitAll())

                                .logout(logout -> logout.permitAll());

                http.authenticationProvider(authProvider);

                return http.build();
        }

}

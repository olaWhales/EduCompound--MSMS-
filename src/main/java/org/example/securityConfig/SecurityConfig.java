package org.example.securityConfig;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests-> requests
                                .requestMatchers(
                                        "/api/teacher/register",
                                        "/api/teacher/invite",
                                        "/user/login",
                                        "/api/superadmin/tenants/registration",
                                        "/api/tenant/setup-password"
                                ).permitAll()
                )

//                .csrf(csrf -> csrf
//                    .ignoringRequestMatchers("/api/teacher/register",
//                            "/api/teacher/invite",
//                            "/user/login",
//                            "/api/superadmin/tenants/registration",
//                            "/api/tenant/setup-password") // Add setup-password to CSRF ignore
//            )
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/superadmin/tenants/registration").hasRole("SUPER_ADMIN")
                    .requestMatchers("/user/login",
                            "/api/teacher/register",
                            "/api/teacher/register/teacher",
                            "/error",
                            "/login",
                            "/api/tenant/setup-password").permitAll()
                    .requestMatchers("/api/teacher/invite",
                            "/api/teacher/update/**",
                            "/api/teacher/delete/",
                            "/api/session/create",
                            "/api/classes/create",
                            "/api/students/register").hasRole("ADMIN")
                    .requestMatchers("/resources/**",
                            "/static/**",
                            "/css/**",
                            "/js/**").permitAll()
                    .requestMatchers("/", "/index.html", "/**").permitAll() // Allow root and static pages temporarily
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .cors(cors -> cors.configurationSource(request -> {
                var config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("http://localhost:5173"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                config.setAllowCredentials(true);
                return config;
            }));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
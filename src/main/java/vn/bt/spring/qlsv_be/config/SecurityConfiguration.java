package vn.bt.spring.qlsv_be.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.bt.spring.qlsv_be.exception.CustomAccessDeniedHandler;
import vn.bt.spring.qlsv_be.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    // Constructor for dependency injection
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/student/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/user/logout/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/user/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/student/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/student/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/student/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/user/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/user/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/user/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

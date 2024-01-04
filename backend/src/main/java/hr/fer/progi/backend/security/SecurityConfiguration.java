package hr.fer.progi.backend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static hr.fer.progi.backend.entity.Permission.*;
import static hr.fer.progi.backend.entity.Role.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /*.exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(authEntryPoint)
                )*/
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/v1/authenticate/**")
                                .permitAll()
                                .requestMatchers("/api/v1/employees/**").hasAnyRole(EMPLOYEE.name(), REVISER.name(), ACCOUNTANT.name(), DIRECTOR.name())

                                .requestMatchers("/api/v1/employee-management/**").hasRole(DIRECTOR.name())
                                .requestMatchers(GET, "/api/v1/employee-management/statistics/**").hasAnyAuthority(ALL_EMPLOYEE_STATISTICS.name(), EMPLOYEE_STATISTICS.name())
                                .requestMatchers(DELETE, "/api/v1/employee-management/delete-account").hasAuthority(DELETE_EMPLOYEE_ACCOUNT.name())

                                .requestMatchers("/api/v1/document/**").hasAnyRole(EMPLOYEE.name(), REVISER.name(), ACCOUNTANT.name(), DIRECTOR.name())
                                .requestMatchers(GET, "/api/v1/document/change-category").hasAuthority(CHANGE_DOCUMENT_CATEGORY.name())

                                .requestMatchers("/api/v1/images/**").hasAnyRole(EMPLOYEE.name(), REVISER.name(), ACCOUNTANT.name(), DIRECTOR.name())

                                .requestMatchers("/api/v1/archive/**").hasAnyRole(REVISER.name(), ACCOUNTANT.name(), DIRECTOR.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/authenticate/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }


}

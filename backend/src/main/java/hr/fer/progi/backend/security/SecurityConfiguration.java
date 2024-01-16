package hr.fer.progi.backend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static hr.fer.progi.backend.entity.Permission.*;
import static hr.fer.progi.backend.entity.Role.*;
import static org.springframework.http.HttpMethod.*;
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
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/v1/authenticate/**")
                                .permitAll()

                                .requestMatchers("/api/v1/employees/get-all-revisers").hasRole(EMPLOYEE.name())
                                .requestMatchers("/api/v1/employees/get-revision-documents").hasRole(REVISER.name())
                                .requestMatchers("/api/v1/employees/**").hasAnyRole(EMPLOYEE.name(), REVISER.name(), ACCOUNTANT_INT_DOC.name(), ACCOUNTANT_OFFER.name(), ACCOUNTANT_RECEIPT.name(),DIRECTOR.name())


                                .requestMatchers("/api/v1/employee-management/**").hasRole(DIRECTOR.name())

                                .requestMatchers("/api/v1/document/change-category").hasAnyRole(ACCOUNTANT_RECEIPT.name(),
                                                                                                    ACCOUNTANT_OFFER.name(),
                                                                                                    ACCOUNTANT_INT_DOC.name(),
                                                                                                    REVISER.name(),
                                                                                                    DIRECTOR.name())

                                .requestMatchers("/api/v1/document/sign-document").hasRole(DIRECTOR.name())
                                .requestMatchers("/api/v1/document/documents-for-sign").hasRole(DIRECTOR.name())
                                .requestMatchers("/api/v1/document/all-documents").hasRole(DIRECTOR.name())
                                .requestMatchers("/api/v1/document/document-history").hasRole(EMPLOYEE.name())
                                .requestMatchers(("/api/v1/document/send-to-sign")).hasAnyRole(ACCOUNTANT_RECEIPT.name(),
                                                                                                ACCOUNTANT_OFFER.name(),
                                                                                                ACCOUNTANT_INT_DOC.name())
                                .requestMatchers("/api/v1/document/send-to-reviser").hasRole(EMPLOYEE.name())
                                .requestMatchers("/api/v1/document/correct").hasAnyRole(EMPLOYEE.name(),
                                                                                            REVISER.name(),
                                                                                            ACCOUNTANT_INT_DOC.name(),
                                                                                            ACCOUNTANT_OFFER.name(),
                                                                                            ACCOUNTANT_RECEIPT.name(),
                                                                                            DIRECTOR.name())
                                .requestMatchers("/api/v1/document/verify").hasAnyRole(REVISER.name())
                                .requestMatchers("/api/v1/document/get-by-type").hasAnyRole(ACCOUNTANT_INT_DOC.name(),
                                                                                                ACCOUNTANT_OFFER.name(),
                                                                                                ACCOUNTANT_RECEIPT.name())
                                .requestMatchers("/api/v1/document/**").hasAnyRole(EMPLOYEE.name(), REVISER.name(), ACCOUNTANT_INT_DOC.name(), ACCOUNTANT_OFFER.name(),ACCOUNTANT_RECEIPT.name(), DIRECTOR.name())

                                .requestMatchers("/api/v1/images/**").hasAnyRole(EMPLOYEE.name(), REVISER.name(), ACCOUNTANT_INT_DOC.name(), ACCOUNTANT_OFFER.name(), ACCOUNTANT_RECEIPT.name(), DIRECTOR.name())

                                .requestMatchers("/api/v1/archive/delete-document").hasRole(DIRECTOR.name())
                                .requestMatchers("/api/v1/archive/**").hasAnyRole(ACCOUNTANT_INT_DOC.name(),
                                                                                    ACCOUNTANT_OFFER.name(),
                                                                                    ACCOUNTANT_RECEIPT.name(),
                                                                                    DIRECTOR.name())

                                .requestMatchers("/api/v1/social/**").hasRole(DIRECTOR.name())


                                .requestMatchers("/api/v1/test/reviser").hasRole(REVISER.name())
                                .requestMatchers("/api/v1/test/director").hasRole(DIRECTOR.name())
                                .requestMatchers("/api/v1/test/**").hasAnyRole(EMPLOYEE.name(), REVISER.name(), ACCOUNTANT_INT_DOC.name(), DIRECTOR.name())
                                .anyRequest()
                                .denyAll()

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

package hr.fer.progi.backend.configuration;


import hr.fer.progi.backend.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final EmployeeDetailsService employeeDetailsService;
    private final TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header == null || !header.startsWith("Bearer ")){
            System.out.println("header was null :(");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        final String employeeEmail = jwtService.extractEmployeeEmail(token);
        System.out.println("employee email " + employeeEmail);
        System.out.println("token prvo "+ token);

        if(employeeEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails employeeDetails = this.employeeDetailsService.loadUserByUsername(employeeEmail);
            System.out.println("tuuuu sasaammm");

            var tok = tokenRepository.findByToken(token);
            System.out.println("token "+ tok);
            boolean isTokenValid = tokenRepository.findByToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            System.out.println("is token valid " + isTokenValid);

            if(jwtService.isTokenValid(token, employeeDetails) && isTokenValid){

                System.out.println("u zadanjem ifu");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        employeeDetails,
                        null,
                        employeeDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }
}

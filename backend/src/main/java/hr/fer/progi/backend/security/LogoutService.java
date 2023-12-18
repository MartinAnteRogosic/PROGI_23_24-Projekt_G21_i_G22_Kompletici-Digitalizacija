package hr.fer.progi.backend.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {


    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;

        if (header == null || !header.startsWith("Bearer ")) {
            return;
        }

        token = header.split(" ")[1].trim();


    }
}

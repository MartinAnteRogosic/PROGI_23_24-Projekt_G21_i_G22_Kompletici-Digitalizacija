package hr.fer.progi.backend.security;

import hr.fer.progi.backend.entity.LoginEntity;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.stat.Statistics;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import hr.fer.progi.backend.repository.StatticsticRepository;
import hr.fer.progi.backend.entity.EmployeeEntity;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final JwtService jwtService;
   private final StatticsticRepository statticsticRepository;
   private final EmployeeRepository employeeRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String email;
        if (header == null || !header.startsWith("Bearer ")) {
            return;
        }



        token = header.split(" ")[1].trim();

       email =  jwtService.extractEmployeeEmail(token);

        EmployeeEntity employeeEntity = employeeRepository.findByEmail(email)
                .orElseThrow(()->new EmployeeNotFoundException(
                        String.format("Employee with email %s ould not be found", email)
                ));
        LoginEntity loginEntity = statticsticRepository.findTopByEmployeeEntityOrderByTimestampLoginDesc(employeeEntity)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        loginEntity.setTimestampLogout(new Timestamp(System.currentTimeMillis()));
        statticsticRepository.save(loginEntity);
    }
}

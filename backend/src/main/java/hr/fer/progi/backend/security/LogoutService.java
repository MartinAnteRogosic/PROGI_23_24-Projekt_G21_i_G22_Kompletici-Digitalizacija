package hr.fer.progi.backend.security;

import hr.fer.progi.backend.entity.LoginLogOutRecordEntity;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import hr.fer.progi.backend.repository.LoginTimeRecordRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final JwtService jwtService;
    private final LoginTimeRecordRepository loginTimeRecordRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            return;
        }


        String token = header.split(" ")[1].trim();

        String email = jwtService.extractEmployeeEmail(token);

        EmployeeEntity employeeEntity = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("Employee with email %s could not be found", email)
                ));

        LoginLogOutRecordEntity loginLogOutRecordEntity = loginTimeRecordRepository.findTopByEmployeeIdOrderByLoginTimeDesc(employeeEntity.getId())
                .orElseThrow(() -> new EmployeeNotFoundException("Record not found"));

        loginLogOutRecordEntity.setLogoutTime(LocalDateTime.now());

        loginTimeRecordRepository.save(loginLogOutRecordEntity);
    }
}

package hr.fer.progi.backend.authentication;


import hr.fer.progi.backend.employee.Employee;
import hr.fer.progi.backend.employee.EmployeeRepository;
import hr.fer.progi.backend.configuration.JwtService;
import hr.fer.progi.backend.token.Token;
import hr.fer.progi.backend.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hr.fer.progi.backend.token.TokenType.BEARER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest request) {
        Employee employee = Employee.builder()
                .employeeName(request.getEmployeeName())
                .employeeSurname(request.getEmployeeSurname())
                .employeeEmail(request.getEmployeeEmail())
                .employeePassword(passwordEncoder.encode(request.getEmployeePassword()))
                .employeeRole(request.getEmployeeRole())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        String token = jwtService.generateToken(employee);
        saveEmployeeToken(savedEmployee, token);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmployeeEmail(),
                        request.getEmployeePassword()
                )
        );

        var employee = employeeRepository.findByEmployeeEmail(request.getEmployeeEmail()).orElseThrow();

        Map<String, Object> claims = new HashMap<>();
        claims.put("employeeName", employee.getEmployeeName());
        claims.put("employeeSurname", employee.getEmployeeSurname());
        claims.put("employeeRole", employee.getEmployeeRole());

        String token = jwtService.generateToken(claims, employee);

        revokeAllEmployeeTokens(employee);
        saveEmployeeToken(employee, token);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();

    }

    private void saveEmployeeToken(Employee employee, String token) {
        Token tokenToSave = Token.builder()
                .employee(employee)
                .token(token)
                .tokenType(BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tokenToSave);
    }

    private void revokeAllEmployeeTokens(Employee employee) {

        List<Token> validEmployeeTokens = tokenRepository.findAllValidTokensByEmployee(employee.getEmployeeId());

        if(validEmployeeTokens.isEmpty()){
            return;
        }

        validEmployeeTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validEmployeeTokens);

    }

}

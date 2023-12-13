package hr.fer.progi.backend.authentication;


import hr.fer.progi.backend.dto.LoginRequestDto;
import hr.fer.progi.backend.dto.LoginResponseDto;
import hr.fer.progi.backend.dto.RegistrationDto;
import hr.fer.progi.backend.dto.RegistrationResponseDto;
import hr.fer.progi.backend.employee.Employee;
import hr.fer.progi.backend.exception.EmployeeNotFound;
import hr.fer.progi.backend.repositroy.EmployeeRepository;
import hr.fer.progi.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegistrationResponseDto register(RegistrationDto registrationDto) {

        if(employeeRepository.existsByEmail(registrationDto.getEmail())){
            return RegistrationResponseDto.builder()
                    .message("Email '" + registrationDto.getEmail() +"' is already taken.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Employee employee = Employee.builder()
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(registrationDto.getRole())
                .build();


        employeeRepository.save(employee);

        return RegistrationResponseDto.builder()
                .message("Registration successful")
                .status(HttpStatus.OK)
                .build();
    }

    public LoginResponseDto login(LoginRequestDto request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Employee employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EmployeeNotFound("Employee could not be found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("employeeName", employee.getFirstName());
        claims.put("employeeSurname", employee.getLastName());
        claims.put("employeeRole", employee.getRole());

        String token = jwtService.generateToken(claims, employee);


        return LoginResponseDto.builder()
                .accessToken(token)
                .build();

    }


}

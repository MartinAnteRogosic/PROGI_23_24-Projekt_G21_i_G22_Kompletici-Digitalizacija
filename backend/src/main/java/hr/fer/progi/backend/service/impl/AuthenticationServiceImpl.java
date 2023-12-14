package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.dto.LoginRequestDto;
import hr.fer.progi.backend.dto.LoginResponseDto;
import hr.fer.progi.backend.dto.RegistrationRequestDto;
import hr.fer.progi.backend.dto.RegistrationResponseDto;
import hr.fer.progi.backend.employee.Employee;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repositroy.EmployeeRepository;
import hr.fer.progi.backend.security.JwtService;
import hr.fer.progi.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {

        if (employeeRepository.existsByEmail(registrationRequestDto.getEmail())) {
            return RegistrationResponseDto.builder()
                    .message("Email '" + registrationRequestDto.getEmail() + "' is already taken.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Employee employee = Employee.builder()
                .firstName(registrationRequestDto.getFirstName())
                .lastName(registrationRequestDto.getLastName())
                .email(registrationRequestDto.getEmail())
                .password(passwordEncoder.encode(registrationRequestDto.getPassword()))
                .role(registrationRequestDto.getRole())
                .build();

        employeeRepository.save(employee);

        return RegistrationResponseDto.builder()
                .message("Registration successful")
                .status(HttpStatus.OK)
                .build();
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        Employee employee = employeeRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("Employee with email '%s' not found", loginRequestDto.getEmail())
                ));

        String token = jwtService.generateToken(employee);


        return LoginResponseDto.builder()
                .tokenType("Bearer ")
                .accessToken(token)
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .role(employee.getRole())
                .id(employee.getId())
                .build();

    }


}

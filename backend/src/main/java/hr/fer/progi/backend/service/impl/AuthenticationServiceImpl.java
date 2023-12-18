package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.dto.*;
import hr.fer.progi.backend.entity.Employee;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repositroy.EmployeeRepository;
import hr.fer.progi.backend.security.JwtService;
import hr.fer.progi.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeServiceImpl employeeService;

    public RegistrationResponseDto register(EmployeeDto employeeDto) {

        Employee employee = employeeService.mapToEntity(employeeDto);

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            return RegistrationResponseDto.builder()
                    .message("Email '" + employee.getEmail() + "' is already taken.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }


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

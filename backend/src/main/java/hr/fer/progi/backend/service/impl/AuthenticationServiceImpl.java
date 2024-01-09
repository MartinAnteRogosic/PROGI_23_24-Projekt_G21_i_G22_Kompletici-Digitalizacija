package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.dto.*;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repository.EmployeeRepository;
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

        EmployeeEntity employeeEntity = employeeService.mapToEntity(employeeDto);

        if (employeeRepository.existsByEmail(employeeEntity.getEmail())) {
            return RegistrationResponseDto.builder()
                    .message("Email '" + employeeEntity.getEmail() + "' is already taken.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }


        employeeRepository.save(employeeEntity);

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

        EmployeeEntity employeeEntity = employeeRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("Employee with email '%s' not found", loginRequestDto.getEmail())
                ));

        String token = jwtService.generateToken(employeeEntity);


        return LoginResponseDto.builder()
                .tokenType("Bearer ")
                .accessToken(token)
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .role(employeeEntity.getRole())
                .id(employeeEntity.getId())
                .build();

    }




}

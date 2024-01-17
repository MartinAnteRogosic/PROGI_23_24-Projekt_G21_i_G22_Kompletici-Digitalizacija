package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.LoginDto;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.LoginLogOutRecordEntity;
import hr.fer.progi.backend.entity.Role;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.exception.RegistrationException;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.repository.LoginTimeRecordRepository;
import hr.fer.progi.backend.security.JwtService;
import hr.fer.progi.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeServiceImpl employeeService;
    private final LoginTimeRecordRepository loginTimeRecordRepository;

    @Override
    public String register(EmployeeDto employeeDto) {

        EmployeeEntity employeeEntity = employeeService.mapToEntity(employeeDto);

        if (employeeRepository.existsByEmail(employeeEntity.getEmail())) {

            throw new RegistrationException("Email '" + employeeEntity.getEmail() + "' is already taken.");
        }

        if (employeeEntity.getRole().equals(Role.DIRECTOR) &&
                employeeRepository.existsByRole(Role.DIRECTOR)) {
            throw new RegistrationException("Director already exists.");
        }


        employeeRepository.save(employeeEntity);

        return "Registration successful";
    }


    @Override
    public LoginDto login(LoginDto loginRequestDto) {

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



        LoginLogOutRecordEntity loginLogOutRecordEntity = LoginLogOutRecordEntity.builder()
                .employee(employeeEntity)
                .loginTime(LocalDateTime.now())
                .build();

        loginTimeRecordRepository.save(loginLogOutRecordEntity);



        return LoginDto.builder()
                .tokenType("Bearer ")
                .accessToken(token)
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .role(employeeEntity.getRole())
                .id(employeeEntity.getId())
                .build();

    }


}

package hr.fer.progi.backend.service;


import hr.fer.progi.backend.dto.LoginDto;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.Role;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticationServiceTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    @AutoConfigureMockMvc
    public void testAuthenticatesUserWithValidCredentials(){
        LoginDto loginRequestDto = LoginDto.builder()
                .email("valid_email@example.com")
                .password("valid_password")
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.EMPLOYEE)
                .build();

        when(employeeRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(employeeEntity));
        when(jwtService.generateToken(employeeEntity)).thenReturn("valid_token");

        // Act
        LoginDto result = authenticationService.login(loginRequestDto);

        // Assert
        assertEquals("Bearer ", result.getTokenType());
        assertEquals("valid_token", result.getAccessToken());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(Role.EMPLOYEE, result.getRole());
        assertEquals(1L, result.getId().longValue());
    }
}

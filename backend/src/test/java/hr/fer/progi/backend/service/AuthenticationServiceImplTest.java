package hr.fer.progi.backend.service;


import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.Role;
import hr.fer.progi.backend.exception.RegistrationException;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.repository.LoginTimeRecordRepository;
import hr.fer.progi.backend.security.JwtService;
import hr.fer.progi.backend.service.impl.AuthenticationServiceImpl;
import hr.fer.progi.backend.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthenticationServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe")
                .role(Role.EMPLOYEE)
                .build();

        // Mock the necessary methods
        when(employeeRepository.existsByEmail(any())).thenReturn(false);

        // Ensure that mapToEntity returns a non-null value
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe")
                .role(Role.EMPLOYEE)
                .build();
        when(employeeService.mapToEntity(any())).thenReturn(employeeEntity);

        // Call the method and assert the result
        assertDoesNotThrow(() -> {
            String result = authenticationService.register(employeeDto);
            assertEquals("Registration successful", result);
        });

        // Verify that the mapToEntity method is called once
        verify(employeeService, times(1)).mapToEntity(any());

        // Verify that the save method is called once
        verify(employeeRepository, times(1)).save(any());
    }




    /*@Test
    void testRegisterEmailAlreadyTaken() {
        EmployeeDto employeeDto = new EmployeeDto("John Doe", "john.doe@example.com", Role.EMPLOYEE);

        when(employeeRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(RegistrationException.class, () -> authenticationService.register(employeeDto));

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testRegisterDirectorAlreadyExists() {
        EmployeeDto employeeDto = new EmployeeDto("Director", "director@example.com", Role.DIRECTOR);

        when(employeeRepository.existsByEmail(any())).thenReturn(false);
        when(employeeRepository.existsByRole(Role.DIRECTOR)).thenReturn(true);

        assertThrows(RegistrationException.class, () -> authenticationService.register(employeeDto));

        verify(employeeRepository, never()).save(any());
    }*/
}

package hr.fer.progi.backend;

import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.LoginDto;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.Role;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TestDataUtil {

    public static EmployeeDto createTestEmployeeDto() {
        return EmployeeDto.builder()
                .firstName("Pero")
                .lastName("Peric")
                .role(Role.EMPLOYEE)
                .email("pero@peric")
                .password("pero123")
                .build();
    }

    public static EmployeeDto createTestEmployeeDtoExistingEmail() {
        return EmployeeDto.builder()
                .firstName("Ivan")
                .lastName("Horvat")
                .role(Role.EMPLOYEE)
                .email("ivan.horvat@gmail.com")
                .password("ivan123")
                .build();
    }


    public static LoginDto createTestLoginDto(){
        return LoginDto.builder()
                .email("pero@peric")
                .password("pero123")
                .build();
    }
}

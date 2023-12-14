package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.employee.Role;
import lombok.Data;

@Data
public class RegistrationRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}

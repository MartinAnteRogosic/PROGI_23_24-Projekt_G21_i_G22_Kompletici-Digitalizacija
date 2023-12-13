package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.employee.Role;
import lombok.Data;

@Data
public class RegistrationDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}

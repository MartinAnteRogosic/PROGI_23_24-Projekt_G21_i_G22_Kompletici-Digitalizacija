package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.employee.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeDto {
    private String firstName;

    private String lastName;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}

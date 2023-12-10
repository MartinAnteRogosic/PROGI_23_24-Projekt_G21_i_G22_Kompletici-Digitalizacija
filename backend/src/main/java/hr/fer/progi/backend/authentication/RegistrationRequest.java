package hr.fer.progi.backend.authentication;

import hr.fer.progi.backend.employee.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String employeeName;
    private String employeeSurname;
    private String employeeEmail;
    private String employeePassword;
    private Role employeeRole;
}

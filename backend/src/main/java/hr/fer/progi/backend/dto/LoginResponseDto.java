package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.employee.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class LoginResponseDto {

    private String accessToken;
    private String tokenType;
    private String firstName;
    private String lastName;
    private Role role;
}

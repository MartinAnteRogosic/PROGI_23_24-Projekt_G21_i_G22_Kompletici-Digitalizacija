package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;
    private String accessToken;
    private String tokenType;
    private String firstName;
    private String lastName;
    private Role role;
    private Long id;

}

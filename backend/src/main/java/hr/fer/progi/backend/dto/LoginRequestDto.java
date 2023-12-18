package hr.fer.progi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequestDto {

    private String email;
    private String password;


}

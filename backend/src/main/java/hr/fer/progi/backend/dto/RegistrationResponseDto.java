package hr.fer.progi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class RegistrationResponseDto {

    private String message;
    private HttpStatus status;

}

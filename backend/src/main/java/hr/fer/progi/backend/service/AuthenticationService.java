package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.LoginRequestDto;
import hr.fer.progi.backend.dto.LoginResponseDto;
import hr.fer.progi.backend.dto.RegistrationRequestDto;
import hr.fer.progi.backend.dto.RegistrationResponseDto;

public interface AuthenticationService {

    RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}

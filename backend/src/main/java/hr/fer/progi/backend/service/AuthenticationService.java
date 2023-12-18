package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.*;

public interface AuthenticationService {

    RegistrationResponseDto register(EmployeeDto registrationRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}

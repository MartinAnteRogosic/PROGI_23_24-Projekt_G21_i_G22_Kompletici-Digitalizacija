package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.LoginDto;

public interface AuthenticationService {

    String register(EmployeeDto registrationRequestDto);

    LoginDto login(LoginDto loginDto);
}

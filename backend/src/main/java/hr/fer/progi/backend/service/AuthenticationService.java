package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.*;
import hr.fer.progi.backend.employee.Employee;

public interface AuthenticationService {

    RegistrationResponseDto register(EmployeeDto registrationRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}

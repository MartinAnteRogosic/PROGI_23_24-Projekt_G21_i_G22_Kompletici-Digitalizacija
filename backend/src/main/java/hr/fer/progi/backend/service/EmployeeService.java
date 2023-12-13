package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangePasswordRequestDto;

import java.security.Principal;

public interface EmployeeService {

    void changePassword(ChangePasswordRequestDto requestDto, Principal connectedEmployee);
}

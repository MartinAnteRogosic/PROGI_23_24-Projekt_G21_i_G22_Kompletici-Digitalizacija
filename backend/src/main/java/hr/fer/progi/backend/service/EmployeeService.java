package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.EmployeeEntity;

import java.security.Principal;
import java.util.List;

public interface EmployeeService {

    String changePassword(ChangePasswordRequestDto requestDto, Principal connectedEmployee);

    EmployeeDto mapToDtoForGetAll(EmployeeEntity employeeEntity);


    List<EmployeeDto> getAllRevisers();


}

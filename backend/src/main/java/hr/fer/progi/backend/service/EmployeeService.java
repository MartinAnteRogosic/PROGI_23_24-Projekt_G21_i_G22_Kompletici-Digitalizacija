package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.employee.Employee;

import java.security.Principal;
import java.util.List;

public interface EmployeeService {

    void changePassword(ChangePasswordRequestDto requestDto, Principal connectedEmployee);


    EmployeeDto mapToDto(Employee employee);
    EmployeeDto mapToDtoForGetAll(Employee employee);
    Employee mapToEntity(EmployeeDto employeeDto);

}

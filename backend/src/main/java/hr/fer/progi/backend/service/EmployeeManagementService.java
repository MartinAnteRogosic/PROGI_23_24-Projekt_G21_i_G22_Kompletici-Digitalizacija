package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.Role;

import java.security.Principal;
import java.util.List;

public interface EmployeeManagementService {

    /*List<?> getAllEmployeeStatistics();

    String getEmployeeStatisticsById();*/

    void deleteEmployee(DeleteEmployeeAccountDto deleteEmployeeAccountDto, Principal connectedEmployee);
    List<EmployeeDto> getAllEmployees();

    void changeRoleOfEmployee(Long employeeId, Role role);

}
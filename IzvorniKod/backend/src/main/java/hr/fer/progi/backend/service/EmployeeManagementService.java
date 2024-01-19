package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.StatisticDto;

import java.security.Principal;
import java.util.List;

public interface EmployeeManagementService {


    String deleteEmployee(DeleteEmployeeAccountDto deleteEmployeeAccountDto, Principal connectedEmployee);
    List<EmployeeDto> getAllEmployees();

    StatisticDto getStatsForEmployee(Long employeeId);

    List<StatisticDto> getAllEmployeeStatistics();
}

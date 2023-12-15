package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeManagementService {

    /*List<?> getAllEmployeeStatistics();

    String getEmployeeStatisticsById();*/

    void deleteEmployee(Long employeeId);
    List<EmployeeDto> getAllEmployees();
}

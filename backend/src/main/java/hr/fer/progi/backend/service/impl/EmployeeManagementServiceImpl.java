package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.employee.Employee;
import hr.fer.progi.backend.employee.Role;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repositroy.EmployeeRepository;
import hr.fer.progi.backend.service.EmployeeManagementService;
import hr.fer.progi.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static hr.fer.progi.backend.employee.Role.DIRECTOR;

@Service
@RequiredArgsConstructor
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeServiceImpl employeeService;

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new EmployeeNotFoundException(
                        String.format("Employee with id %d could not be found", employeeId)
                ));

        employeeRepository.delete(employee);

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        List<Employee> listOfEmployees = employeeRepository.findAll();
        listOfEmployees.removeIf(employee -> employee.getRole().equals(DIRECTOR));

        return listOfEmployees.stream()
                .map(employeeService::mapToDtoForGetAll)
                .collect(Collectors.toList());
    }
}

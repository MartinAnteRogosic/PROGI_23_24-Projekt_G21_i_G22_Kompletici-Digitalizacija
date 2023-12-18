package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.Employee;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repositroy.EmployeeRepository;
import hr.fer.progi.backend.service.EmployeeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static hr.fer.progi.backend.entity.Role.DIRECTOR;

@Service
@RequiredArgsConstructor
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeServiceImpl employeeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void deleteEmployee(DeleteEmployeeAccountDto deleteEmployeeAccountDto, Principal connectedEmployee) {

        Employee director = (Employee) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();

        if(!passwordEncoder.matches(deleteEmployeeAccountDto.getDirectorPassword(), director.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }


        Employee employee = employeeRepository.findById(deleteEmployeeAccountDto.getEmployeeId())
                .orElseThrow(()->new EmployeeNotFoundException(
                        String.format("Employee with id %d could not be found", deleteEmployeeAccountDto.getEmployeeId())
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

package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.StatisticDto;
import hr.fer.progi.backend.entity.Role;
import hr.fer.progi.backend.service.impl.EmployeeManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/employee-management")
public class EmployeeManagementController {

    private final EmployeeManagementServiceImpl employeeManagementService;

    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticDto>> getAllEmployeeStatistics(){
            List<StatisticDto> employeeStatistics = employeeManagementService.getAllEmployeeStatistics();
            return new ResponseEntity<>(employeeStatistics, HttpStatus.OK);
    }

    @GetMapping("/statistics-for-employee")
    public ResponseEntity<StatisticDto> getEmployeeStatisticById(@RequestBody EmployeeDto employeeDto){
         StatisticDto statisticDto = employeeManagementService.getStatsForEmployee(employeeDto.getId());
        return new ResponseEntity<>(statisticDto, HttpStatus.OK);
    }
    @GetMapping("/all-employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> listOfEmployees = employeeManagementService.getAllEmployees();
        return new ResponseEntity<>(listOfEmployees, HttpStatus.OK);
    }

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteEmployeeById(@RequestBody DeleteEmployeeAccountDto deleteEmployeeAccountDto, Principal connectedEmployee){
       String response =  employeeManagementService.deleteEmployee(deleteEmployeeAccountDto, connectedEmployee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package hr.fer.progi.backend.controller;


import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.service.impl.EmployeeManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employee-management")
public class EmployeeManagementController {

    private final EmployeeManagementServiceImpl employeeManagementService;

    @GetMapping("/statistics")
    public ResponseEntity<?> getAllEmployeeStatistics(){
        return new ResponseEntity<>("All employee statistics", HttpStatus.OK);
    }

    @GetMapping("/statistics/{employeeId}")
    public ResponseEntity<?> getEmployeeStatisticById(@PathVariable(value = "employeeId") Long employeeId){
        return new ResponseEntity<>(String.format("Statistics of employee with id %d", employeeId), HttpStatus.OK);
    }
    @GetMapping("/all-employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> listOfEmployees = employeeManagementService.getAllEmployees();
        return new ResponseEntity<>(listOfEmployees, HttpStatus.OK);
    }
    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteEmployeeById(@RequestBody DeleteEmployeeAccountDto deleteEmployeeAccountDto, Principal connectedEmployee){
        employeeManagementService.deleteEmployee(deleteEmployeeAccountDto, connectedEmployee);

        return new ResponseEntity<>("Successfully deleted employee account", HttpStatus.OK);
    }
}

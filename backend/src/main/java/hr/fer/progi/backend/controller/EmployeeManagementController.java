package hr.fer.progi.backend.controller;


import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.Role;
import hr.fer.progi.backend.service.impl.EmployeeManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/employee-management")
public class EmployeeManagementController {

    private final EmployeeManagementServiceImpl employeeManagementService;
    private final DocumentServiceImpl documentService;
    @GetMapping("/statistics")
    public ResponseEntity<?> getAllEmployeeStatistics(){
            List<EmployeeDto> listOfEmployees = employeeManagementService.getAllEmployees();
            return new ResponseEntity<>(listOfEmployees, HttpStatus.OK);
    }

    @GetMapping("/statistics/{employeeId}")
    public ResponseEntity<?> getEmployeeStatisticById(@PathVariable(value = "employeeId") Long employeeId){
         List<DocumentEntity> listOfDocuments = documentService.getAllDocumentsForUser(employeeId);
         Integer numberOfDocuments = listOfDocuments.size();
        return new ResponseEntity<>(String.format("Statistics of employee with id %d is %d", employeeId,numberOfDocuments), HttpStatus.OK);
    }
    @GetMapping("/all-employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> listOfEmployees = employeeManagementService.getAllEmployees();
        return new ResponseEntity<>(listOfEmployees, HttpStatus.OK);
    }

    @GetMapping("all-employees/{employeeId}")
    public ResponseEntity<?>  changeRoleOfEmplye(@RequestParam Role role, @PathVariable Long employeeId){
            employeeManagementService.changeRoleOfEmployee(employeeId,role);
        return new ResponseEntity<>(String.format("Function successfully changed for employee with id %d", employeeId), HttpStatus.OK);



    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteEmployeeById(@RequestBody DeleteEmployeeAccountDto deleteEmployeeAccountDto, Principal connectedEmployee){
        employeeManagementService.deleteEmployee(deleteEmployeeAccountDto, connectedEmployee);

        return new ResponseEntity<>("Successfully deleted employee account", HttpStatus.OK);
    }
}

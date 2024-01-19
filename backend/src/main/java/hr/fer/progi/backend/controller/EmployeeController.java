package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @GetMapping("/home")
    public ResponseEntity<String> home() {

        return ResponseEntity.ok("Employee home page");
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequestDto request,
            Principal connectedEmployee
    ) {

        String response = employeeService.changePassword(request, connectedEmployee);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    @GetMapping("/get-all-revisers")
    public ResponseEntity<?> getAllRevisers() {
        List<EmployeeDto> listOfRevisers = employeeService.getAllRevisers();

        return new ResponseEntity<>(listOfRevisers, HttpStatus.OK);
    }

}

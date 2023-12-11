package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.employee.ChangePasswordRequest;
import hr.fer.progi.backend.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/landing")
    public ResponseEntity<String> landing(){
        return ResponseEntity.ok("Employee landing page :)");
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedEmployee
    ){

        employeeService.changePassword(request, connectedEmployee);

        return ResponseEntity.accepted().build();
    }
}

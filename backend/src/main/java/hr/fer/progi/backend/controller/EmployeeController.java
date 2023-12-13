package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;
    @GetMapping("/home")
    public ResponseEntity<String> landing(){

        return ResponseEntity.ok("Employee home page :)");
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequestDto request,
            Principal connectedEmployee
    ){

        employeeService.changePassword(request, connectedEmployee);

        return ResponseEntity.accepted().build();
    }
}

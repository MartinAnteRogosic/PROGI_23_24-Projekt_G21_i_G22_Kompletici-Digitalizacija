package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @GetMapping("/landing")
    public ResponseEntity<String> landing(){
        return ResponseEntity.ok("Employee landing page :)");
    }

    /*@PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedEmployee
    ){

        employeeService.changePassword(request, connectedEmployee);

        return ResponseEntity.accepted().build();
    }*/
}

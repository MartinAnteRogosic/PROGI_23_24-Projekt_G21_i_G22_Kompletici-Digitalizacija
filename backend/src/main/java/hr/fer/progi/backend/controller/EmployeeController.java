package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.LoginEntity;
import hr.fer.progi.backend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequestMapping("/")
@CrossOrigin
public class EmployeeController {


    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeEntity employeeEntity){
        employeeService.saveEmployee(employeeEntity);
        return ResponseEntity.ok("Employee registered.");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginEntity loginEntity){
        EmployeeEntity employeeEntity = employeeService.findByUserEmail(loginEntity.getEmail());

        if(employeeEntity == null){
            return "Account doesn't exist please register.";
        }else if(Objects.equals(employeeEntity.getUserPassword(), loginEntity.getPassword())){
            return "Successful login";
        }else{
            return "Wrong password";
        }
    }
    @PutMapping("/update")
    public EmployeeEntity updateEmployee(@RequestBody EmployeeEntity employeeEntity){
        return employeeService.updateEmployee(employeeEntity);
    }

    @DeleteMapping("/{userId}")
    public void deleteEmployee(@PathVariable("userId") String userId){
        employeeService.deleteEmployee(userId);
    }






}

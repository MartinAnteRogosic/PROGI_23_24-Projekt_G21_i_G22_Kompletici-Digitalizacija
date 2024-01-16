package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.LoginEntity;
import hr.fer.progi.backend.service.EmployeeService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginEntity loginEntity){
        EmployeeEntity employeeEntity = employeeService.findByUserEmail(loginEntity.getEmail());



        if(employeeEntity == null){
            JSONObject jo = new JSONObject();
            jo.put("message", "Employee doesn't exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jo.toString());

        }else if(Objects.equals(employeeEntity.getUserPassword(), loginEntity.getPassword())){
            JSONObject employee = new JSONObject();
            employee.put("userName", employeeEntity.getUserName());
            employee.put("userFunction", employeeEntity.getUserFunction());
            return ResponseEntity.status(HttpStatus.OK).body(employee.toString());
        }else{
            JSONObject jo = new JSONObject();
            jo.put("message", "Wrong password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jo.toString());
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

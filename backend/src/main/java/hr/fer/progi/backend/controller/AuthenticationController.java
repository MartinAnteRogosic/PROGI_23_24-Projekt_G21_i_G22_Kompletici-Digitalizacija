package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.LoginDto;
import hr.fer.progi.backend.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EmployeeDto registrationRequestDto){
        String response = authenticationServiceImpl.register(registrationRequestDto);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody LoginDto request){
        LoginDto response = authenticationServiceImpl.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}

package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.service.impl.AuthenticationServiceImpl;
import hr.fer.progi.backend.dto.LoginRequestDto;
import hr.fer.progi.backend.dto.LoginResponseDto;
import hr.fer.progi.backend.dto.RegistrationRequestDto;
import hr.fer.progi.backend.dto.RegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDto registrationRequestDto){
        RegistrationResponseDto response = authenticationServiceImpl.register(registrationRequestDto);

        return  new ResponseEntity<>(response.getMessage(), response.getStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){

        return ResponseEntity.ok(authenticationServiceImpl.login(request));
    }
}

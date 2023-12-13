package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.authentication.AuthenticationResponse;
import hr.fer.progi.backend.authentication.AuthenticationService;
import hr.fer.progi.backend.authentication.LoginRequest;
import hr.fer.progi.backend.authentication.RegistrationRequest;
import hr.fer.progi.backend.dto.RegistrationDto;
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

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDto registrationDto){
        RegistrationResponseDto response = authenticationService.register(registrationDto);

        return  new ResponseEntity<>(response.getMessage(), response.getStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}

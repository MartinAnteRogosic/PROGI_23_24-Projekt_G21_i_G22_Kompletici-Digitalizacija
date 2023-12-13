package hr.fer.progi.backend.authentication;


import hr.fer.progi.backend.dto.LoginRequestDto;
import hr.fer.progi.backend.dto.LoginResponseDto;
import hr.fer.progi.backend.dto.RegistrationDto;
import hr.fer.progi.backend.dto.RegistrationResponseDto;
import hr.fer.progi.backend.employee.Employee;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repositroy.EmployeeRepository;
import hr.fer.progi.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegistrationResponseDto register(RegistrationDto registrationDto) {

        if(employeeRepository.existsByEmail(registrationDto.getEmail())){
            return RegistrationResponseDto.builder()
                    .message("Email '" + registrationDto.getEmail() +"' is already taken.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Employee employee = Employee.builder()
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(registrationDto.getRole())
                .build();


        employeeRepository.save(employee);

        return RegistrationResponseDto.builder()
                .message("Registration successful")
                .status(HttpStatus.OK)
                .build();
    }

    public LoginResponseDto login(LoginRequestDto request){


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Employee employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with email '%s' not found", request.getEmail())));
        System.out.println("after authentication manager");

        String token = jwtService.generateToken(employee);



        return LoginResponseDto.builder()
                .tokenType("Bearer ")
                .accessToken(token)
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .role(employee.getRole())
                .build();

    }


}

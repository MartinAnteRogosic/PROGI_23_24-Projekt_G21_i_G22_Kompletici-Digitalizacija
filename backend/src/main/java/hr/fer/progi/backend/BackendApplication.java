package hr.fer.progi.backend;

import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.employee.Employee;
import hr.fer.progi.backend.employee.Role;
import hr.fer.progi.backend.service.impl.AuthenticationServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static hr.fer.progi.backend.employee.Role.*;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationServiceImpl authenticationService){
		return args -> {
			EmployeeDto director = EmployeeDto.builder()
					.firstName("Ivan")
					.lastName("Horvat")
					.email("ivan.horvat@gmail.com")
					.password("ivanpass")
					.role(DIRECTOR)
					.build();

			EmployeeDto accountant = EmployeeDto.builder()
					.firstName("Ana")
					.lastName("Kovač")
					.email("ana.kovač@gmail.com")
					.password("anapass")
					.role(ACCOUNTANT)
					.build();

			EmployeeDto reviser = EmployeeDto.builder()
					.firstName("Petar")
					.lastName("Horvat")
					.email("petar.horvat@gmail.com")
					.password("petar123")
					.role(REVISER)
					.build();

			EmployeeDto employee = EmployeeDto.builder()
					.firstName("Ivana")
					.lastName("Novak")
					.email("ivana.novak@gmail.com")
					.password("ivana123")
					.role(EMPLOYEE)
					.build();

			authenticationService.register(director);
			authenticationService.register(accountant);
			authenticationService.register(reviser);
			authenticationService.register(employee);
		};
	}
}

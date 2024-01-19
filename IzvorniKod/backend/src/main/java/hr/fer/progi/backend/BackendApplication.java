package hr.fer.progi.backend;

import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.service.impl.AuthenticationServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static hr.fer.progi.backend.entity.Role.*;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

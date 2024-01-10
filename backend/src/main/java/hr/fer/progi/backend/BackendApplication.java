package hr.fer.progi.backend;

import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.service.impl.ArchiveServiceImpl;
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

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationServiceImpl authenticationService,
			DocumentRepository documentRepository,
			ArchiveServiceImpl archiveService,
			EmployeeRepository employeeRepository
			) {
		return args -> {

			/* adding test users */
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


			EmployeeEntity accountant_entity = employeeRepository.findById(2L).orElseThrow(()->new EmployeeNotFoundException("not found"));
			/* adding test documents */
			DocumentEntity racun = DocumentEntity.builder()
					.type(DocumentType.RAČUN)
					.url("url_račun")
					.scanEmployee(accountant_entity)
					.build();

			DocumentEntity ponuda = DocumentEntity.builder()
					.type(DocumentType.PONUDA)
					.scanEmployee(accountant_entity)
					.url("url_ponuda")
					.build();

			documentRepository.save(racun);
			documentRepository.save(ponuda);
			archiveService.archiveDocument(1L);
			archiveService.archiveDocument(2L);

		};
	}
}

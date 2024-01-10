package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.service.EmployeeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static hr.fer.progi.backend.entity.Role.DIRECTOR;

@Service
@RequiredArgsConstructor
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeServiceImpl employeeService;
    private final PasswordEncoder passwordEncoder;
    private final DocumentRepository documentRepository;

    @Override
    public void deleteEmployee(DeleteEmployeeAccountDto deleteEmployeeAccountDto, Principal connectedEmployee) {

        EmployeeEntity director = (EmployeeEntity) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();

        if(!passwordEncoder.matches(deleteEmployeeAccountDto.getDirectorPassword(), director.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }


        EmployeeEntity employeeEntity = employeeRepository.findById(deleteEmployeeAccountDto.getEmployeeId())
                .orElseThrow(()->new EmployeeNotFoundException(
                        String.format("Employee with id %d could not be found", deleteEmployeeAccountDto.getEmployeeId())
                ));

        List<DocumentEntity> listOfDocuments = employeeEntity.getScannedDocuments();
        listOfDocuments.stream().map(document -> {
            document.setScanEmployee(null);
            return document;
        }).collect(Collectors.toList());

        documentRepository.saveAll(listOfDocuments);

        employeeRepository.delete(employeeEntity);

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        List<EmployeeEntity> listOfEmployeeEntities = employeeRepository.findAll();
        listOfEmployeeEntities.removeIf(employee -> employee.getRole().equals(DIRECTOR));

        return listOfEmployeeEntities.stream()
                .map(employeeService::mapToDtoForGetAll)
                .collect(Collectors.toList());
    }
}

package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.DeleteEmployeeAccountDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.LoginTime;
import hr.fer.progi.backend.dto.StatisticDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.Role;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.repository.LoginTimeRecordRepository;
import hr.fer.progi.backend.service.EmployeeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hr.fer.progi.backend.entity.Role.DIRECTOR;

@Service
@RequiredArgsConstructor
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeServiceImpl employeeService;
    private final PasswordEncoder passwordEncoder;
    private final DocumentRepository documentRepository;
    private final LoginTimeRecordRepository loginTimeRecordRepository;

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

        List<DocumentEntity> listOfScannedDocuments = employeeEntity.getScannedDocuments();
        listOfScannedDocuments.stream().map(document -> {
            document.setScanEmployee(null);
            return document;
        }).collect(Collectors.toList());

        List<DocumentEntity> listOfValidatedDocuments = employeeEntity.getRevisedDocuments();
        listOfValidatedDocuments.stream().map(document -> {
            document.setVerificationEmployee(null);
            return document;
        }).collect(Collectors.toList());

        documentRepository.saveAll(listOfScannedDocuments);
        documentRepository.saveAll(listOfValidatedDocuments);


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

    @Override
    public void changeRoleOfEmployee(Long employeeId, Role role) {

           EmployeeEntity employee = employeeRepository.findById(employeeId)
                   .orElseThrow(()->new EmployeeNotFoundException(
                           String.format("Employee with id %d could not be found", employeeId)
                   ));

           employee.setRole(role);
           employeeRepository.save(employee);
    }

    @Override
    public StatisticDto getStatsForEmployee(Long employeeId) {

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not found"));

        int numberOfScannedDocument = employee.getScannedDocuments().size();
        int numberOfRevisedDocument = employee.getRevisedDocuments().size();

        Map<String, Long> totalTimePerDate = loginTimeRecordRepository.findAllByEmployeeIdAndLogoutTimeIsNotNull(employee.getId()).stream()
                .collect(Collectors.groupingBy(
                        record -> record.getLoginTime().toLocalDate().toString(),
                        Collectors.summingLong(record -> Duration.between(
                                        record.getLoginTime(),
                                        record.getLogoutTime())
                                .toSeconds())
                ));


        List<LoginTime> timePerDate = totalTimePerDate.entrySet().stream()
                .map(entry -> LoginTime.builder()
                        .date(entry.getKey())
                        .totalTime(entry.getValue())
                        .build()).collect(Collectors.toList());




        return StatisticDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .role(employee.getRole())
                .numberOfScannedDocuments(numberOfScannedDocument)
                .numberOfRevisedDocuments(numberOfRevisedDocument)
                .loginTimes(timePerDate)
                .build();
    }

    @Override
    public List<StatisticDto> getAllEmployeeStatistics() {
        List<EmployeeEntity> listOfEmployees = employeeRepository.findAll();

        return listOfEmployees.stream()
                .map(employee -> getStatsForEmployee(employee.getId()))
                .collect(Collectors.toList());
    }
}

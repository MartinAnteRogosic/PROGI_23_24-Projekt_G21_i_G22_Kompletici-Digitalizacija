package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.entity.LoginEntity;
import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.Role;
import hr.fer.progi.backend.exception.ChangePasswordException;
import hr.fer.progi.backend.exception.NoRevisersFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import hr.fer.progi.backend.dto.StatisticDto;

import java.security.Principal;
import java.time.Duration;
import java.util.List;

import hr.fer.progi.backend.repository.StatticsticRepository;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final DocumentServiceImpl documentService;
    private final DocumentRepository documentRepository;
    private final StatticsticRepository statticsticRepository;
    private final EmployeeServiceImpl employeeServiceImpl;

    public void changePassword(ChangePasswordRequestDto request, Principal connectedEmployee) {

        EmployeeEntity employeeEntity = (EmployeeEntity) ((UsernamePasswordAuthenticationToken) connectedEmployee).getPrincipal();

        /*checking is the current password is correct*/
        if (!passwordEncoder.matches(request.getOldPassword(), employeeEntity.getPassword())) {
            throw new ChangePasswordException("Wrong old password");
        }


        //check if new password and password confirmation are the same
        if (!request.getNewPassword().equals(request.getPasswordConfirmation())) {
            throw new ChangePasswordException("Password and password confirmation are not the same");
        }

        if (passwordEncoder.matches(request.getNewPassword(), employeeEntity.getPassword())) {
            throw new ChangePasswordException("New password can't be the same as the old one.");
        }


        employeeEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));

        //save employee with new password
        employeeRepository.save(employeeEntity);


    }


    @Override
    public EmployeeDto mapToDto(EmployeeEntity employeeEntity) {
        return EmployeeDto.builder()
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .email(employeeEntity.getEmail())
                .role(employeeEntity.getRole())
                .build();
    }

    @Override
    public EmployeeEntity mapToEntity(EmployeeDto employeeDto) {
        return EmployeeEntity.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .role(employeeDto.getRole())
                .build();
    }

    @Override
    public List<EmployeeDto> getAllRevisers() {
        List<EmployeeEntity> revisers = employeeRepository.findByRole(Role.REVISER);

        if (revisers.isEmpty()) {
            throw new NoRevisersFoundException("There are no revisers in the database");
        }

        return revisers.stream()
                .map(this::mapToDtoForGetAll)
                .toList();
    }



    @Override
    public long getTimeForEmployee(Long employeeId) {
     List<LoginEntity> loginEntity = statticsticRepository.findAllById(employeeId);
        return  loginEntity.stream()
                .filter(loginEntity1 -> loginEntity1.getTimestampLogin() != null && loginEntity1.getTimestampLogout() != null)
                .mapToLong(loginEntity1 -> Duration.between(
                        loginEntity1.getTimestampLogin().toInstant(),
                        loginEntity1.getTimestampLogout().toInstant()
                ).toMinutes())
                .sum();

    }

    @Override
    public StatisticDto getStatsForEmployee(Long employeeId) {

        List<DocumentEntity> listOfDocuments = documentService.getAllDocumentsForUser(employeeId);
        long numberOfDocuments = (long) listOfDocuments.size();
        long timeOfLogin = employeeServiceImpl.getTimeForEmployee(employeeId);

        return StatisticDto.builder()
                .timeOfLogin(timeOfLogin)
                .numberOfDocuments(numberOfDocuments)
                .build();
    }

    @Override
    public EmployeeDto mapToDtoForGetAll(EmployeeEntity employeeEntity) {

        return EmployeeDto.builder()
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .id(employeeEntity.getId())
                .role(employeeEntity.getRole())
                .build();
    }

}

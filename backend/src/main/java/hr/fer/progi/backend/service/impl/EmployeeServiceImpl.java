package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.exception.ChangePasswordException;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    public void changePassword(ChangePasswordRequestDto request, Principal connectedEmployee) {

        EmployeeEntity employeeEntity = (EmployeeEntity) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();

        /*checking is the current password is correct*/
        if(!passwordEncoder.matches(request.getOldPassword(), employeeEntity.getPassword())){
            throw new ChangePasswordException("Wrong old password");
        }



        //check if new password and password confirmation are the same
        if(!request.getNewPassword().equals(request.getPasswordConfirmation())){
            throw new ChangePasswordException("Password and password confirmation are not the same");
        }

        if(passwordEncoder.matches(request.getNewPassword(), employeeEntity.getPassword())){
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
    public EmployeeDto mapToDtoForGetAll(EmployeeEntity employeeEntity) {

        return EmployeeDto.builder()
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .id(employeeEntity.getId())
                .role(employeeEntity.getRole())
                .build();
    }

}

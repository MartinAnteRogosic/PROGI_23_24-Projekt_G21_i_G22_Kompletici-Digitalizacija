package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.employee.Employee;
import hr.fer.progi.backend.exception.ChangePasswordException;
import hr.fer.progi.backend.repositroy.EmployeeRepository;
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

        Employee employee = (Employee) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();

        /*checking is the current password is correct*/
        if(!passwordEncoder.matches(request.getOldPassword(), employee.getPassword())){
            throw new ChangePasswordException("Wrong old password");
        }



        //check if new password and password confirmation are the same
        if(!request.getNewPassword().equals(request.getPasswordConfirmation())){
            throw new ChangePasswordException("Password and password confirmation are not the same");
        }

        if(passwordEncoder.matches(request.getNewPassword(), employee.getPassword())){
            throw new ChangePasswordException("New password can't be the same as the old one.");
        }


        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));

        //save employee with new password
        employeeRepository.save(employee);


    }

}

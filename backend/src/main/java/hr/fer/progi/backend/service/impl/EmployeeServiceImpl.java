package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.repositroy.EmployeeRepository;
import hr.fer.progi.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    /*public void changePassword(ChangePasswordRequest request, Principal connectedEmployee) {

        Employee employee = (Employee) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();

        *//*checking is the current password is correct*//*
        if(!passwordEncoder.matches(request.getOldPassword(), employee.getEmployeePassword())){
            throw new IllegalStateException("Wrong old password");
        }

        //check if new password and password confirmation are the same
        if(!request.getNewPassword().equals(request.getPasswordConfirmation())){
            throw new IllegalStateException("Passwords are not the same");
        }


        employee.setEmployeePassword(passwordEncoder.encode(request.getNewPassword()));

        //save employee with new password
        employeeRepository.save(employee);


    }*/

}

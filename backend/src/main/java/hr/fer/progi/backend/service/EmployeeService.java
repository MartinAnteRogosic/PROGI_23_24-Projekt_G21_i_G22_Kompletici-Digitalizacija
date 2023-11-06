package hr.fer.progi.backend.service;

import hr.fer.progi.backend.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface EmployeeService {

    Optional<EmployeeEntity> findById(String userId);

    EmployeeEntity saveEmployee(EmployeeEntity employeeEntity);
    EmployeeEntity updateEmployee(EmployeeEntity employeeEntity);
    void deleteEmployee(String userId);

}

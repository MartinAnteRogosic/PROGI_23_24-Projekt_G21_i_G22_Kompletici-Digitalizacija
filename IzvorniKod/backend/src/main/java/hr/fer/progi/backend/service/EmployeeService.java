package hr.fer.progi.backend.service;

import hr.fer.progi.backend.entity.EmployeeEntity;
import java.util.Optional;

public interface EmployeeService {

    Optional<EmployeeEntity> findById(String userId);

    EmployeeEntity findByUserEmail(String userEmail);
    void saveEmployee(EmployeeEntity employeeEntity);
    EmployeeEntity updateEmployee(EmployeeEntity employeeEntity);
    void deleteEmployee(String userId);

}

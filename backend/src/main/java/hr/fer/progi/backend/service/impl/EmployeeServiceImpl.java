package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository  employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    @Override
    public Optional<EmployeeEntity> findById(String userId) {

        return employeeRepository.findById(userId);
    }

    @Override
    public EmployeeEntity saveEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    @Override
    public EmployeeEntity updateEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    @Override
    public void deleteEmployee(String userId) {

        employeeRepository.deleteById(userId);

    }
}

package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    Boolean existsByEmail(String email);
}

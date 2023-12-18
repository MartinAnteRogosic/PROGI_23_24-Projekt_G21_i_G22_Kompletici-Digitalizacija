package hr.fer.progi.backend.repositroy;

import hr.fer.progi.backend.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    Boolean existsByEmail(String email);
}

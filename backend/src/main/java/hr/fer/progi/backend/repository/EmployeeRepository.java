package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByEmail(String email);

    Boolean existsByEmail(String email);
}

package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import hr.fer.progi.backend.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface StatticsticRepository extends JpaRepository<LoginEntity,Long> {

         Optional<LoginEntity> findTopByEmployeeEntityOrderByTimestampLoginDesc(EmployeeEntity employeeEntity);

    List<LoginEntity> findAllById(Long employeeId);

}

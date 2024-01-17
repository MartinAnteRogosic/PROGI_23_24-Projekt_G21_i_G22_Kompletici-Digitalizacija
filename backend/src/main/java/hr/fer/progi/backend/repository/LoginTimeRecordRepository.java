package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.LoginLogOutRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginTimeRecordRepository extends JpaRepository<LoginLogOutRecordEntity, Long> {

    Optional<LoginLogOutRecordEntity> findTopByEmployeeIdOrderByLoginTimeDesc(Long employeeId);

    List<LoginLogOutRecordEntity> findAllById(Long employeeId);

}

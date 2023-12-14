package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.ArchiveRecieptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRecieptRepository extends JpaRepository<ArchiveRecieptEntity, String> {
    // Additional custom queries can be added here if needed
}

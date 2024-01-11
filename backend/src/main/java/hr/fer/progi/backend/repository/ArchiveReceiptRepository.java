package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.ArchiveReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveReceiptRepository extends JpaRepository<ArchiveReceiptEntity, Long> {
    // Additional custom queries can be added here if needed
}

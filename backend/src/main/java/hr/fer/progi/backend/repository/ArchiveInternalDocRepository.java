package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.ArchiveInternalDocEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveInternalDocRepository extends JpaRepository<ArchiveInternalDocEntity, String> {
    // Additional custom queries can be added here if needed
}

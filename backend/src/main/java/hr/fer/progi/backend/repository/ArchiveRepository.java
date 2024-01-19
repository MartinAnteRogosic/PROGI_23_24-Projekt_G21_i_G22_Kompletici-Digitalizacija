package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.ArchiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveRepository extends JpaRepository<ArchiveEntity, Long>{

    Optional<ArchiveEntity> findByDocumentId(Long documentId);
    Boolean existsByDocumentId(Long documentId);
}

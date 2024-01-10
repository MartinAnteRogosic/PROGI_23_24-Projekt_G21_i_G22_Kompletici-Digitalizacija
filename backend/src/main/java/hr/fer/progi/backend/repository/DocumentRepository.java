package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    List<DocumentEntity> findByType(DocumentType documentType);

    @Query("SELECT d FROM DocumentEntity d WHERE d.id = :userId")
    List<DocumentEntity> findAllById(Long userId);
}
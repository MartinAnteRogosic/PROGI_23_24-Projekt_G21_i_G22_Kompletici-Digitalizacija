package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByType(DocumentType documentType);

    @Query("SELECT d FROM Document d WHERE d.id = :userId")
    List<Document> findAllById(Long userId);
}

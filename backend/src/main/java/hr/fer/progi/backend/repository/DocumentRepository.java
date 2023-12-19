package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByType(DocumentType documentType);
}

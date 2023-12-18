package hr.fer.progi.backend.repositroy;

import hr.fer.progi.backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}

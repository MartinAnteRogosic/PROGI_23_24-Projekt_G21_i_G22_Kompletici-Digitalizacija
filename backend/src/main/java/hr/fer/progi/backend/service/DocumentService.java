package hr.fer.progi.backend.service;

import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DocumentEntity> getDocumentsByType(String documentType) {
        return documentRepository.findByDocumentType(documentType);
    }

    public DocumentEntity getDocumentById(String documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }
}

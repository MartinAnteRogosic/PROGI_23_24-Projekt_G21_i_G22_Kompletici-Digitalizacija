package hr.fer.progi.backend.service;

import hr.fer.progi.backend.entity.DocumentEntity;

import java.util.List;

public interface DocumentService {

    List<DocumentEntity> getDocumentsByType(String documentType);

    DocumentEntity getDocumentById(String documentId);
}

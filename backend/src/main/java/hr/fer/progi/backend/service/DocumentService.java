package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.PhotoEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DocumentService {

    List<DocumentEntity> getDocumentsByType(DocumentType documentType);

    DocumentEntity getDocumentById(Long documentId);

    ChangeCategoryDto getPhotoAndDocument(ChangeCategoryDto changeCategoryDto);

    String changeCategory(ChangeCategoryDto changeCategoryDto);
    List<DocumentEntity> getAllDocumentsForUser(Long userId);
    PhotoEntity getPhotoById(Long photoId);

    List<DocumentEntity> getAllVerifedDocuments();

    ResponseEntity<String> setDocumentToBeSinged(Long documentId);

    List<DocumentEntity> getAllDocumentsForSign();

    ResponseEntity<String> signDocument(Long documentId);

    ResponseEntity<String> refuseSign(Long documentId);
}

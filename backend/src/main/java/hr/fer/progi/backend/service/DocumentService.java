package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.PhotoEntity;

import java.security.Principal;
import java.util.List;

public interface DocumentService {

    List<PhotoDocumentDto> getDocumentsByType(DocumentType documentType);

    DocumentEntity getDocumentById(Long documentId);

    String changeDocumentType(DocumentDto documentDto);

    List<DocumentEntity> getAllDocumentsForUser(Long userId);

    List<DocumentDto> getDocumentsByVerificationEmployeeId(Long employeeId);
    PhotoEntity getPhotoById(Long photoId);

    List<DocumentEntity> getAllVerifiedDocuments();

    String setDocumentToBeSinged(DocumentDto documentDto);

    List<DocumentDto> getAllDocumentsForSigning();

    List<PhotoDocumentDto> getDocumentHistory(Principal connectedEmployee);

    String signDocument(DocumentDto documentDto);


    List<PhotoDocumentDto> getAllDocuments();

    void sendToReviser(ChooseReviserDto choosereviserdto);

    String setCorrect(DocumentDto documentDto);

    String setVerified(DocumentDto documentDto);

    DocumentType categorizeDocument(String documentText);

    List<PhotoDocumentDto> getAllUnconfirmedDocuments();
}

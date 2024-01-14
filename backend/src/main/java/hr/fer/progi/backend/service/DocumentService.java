package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.PhotoEntity;

import java.util.List;

public interface DocumentService {

    List<DocumentDto> getDocumentsByType(DocumentType documentType);

    DocumentEntity getDocumentById(Long documentId);

    ChangeCategoryDto getPhotoAndDocument(ChangeCategoryDto changeCategoryDto);

    String changeCategory(ChangeCategoryDto changeCategoryDto);
    List<DocumentEntity> getAllDocumentsForUser(Long userId);

    List<DocumentDto> getDocumentsByVerificationEmployeeId(Long employeeId);
    PhotoEntity getPhotoById(Long photoId);



    List<DocumentEntity> getAllVerifiedDocuments();

    String setDocumentToBeSinged(DocumentDto documentDto);

    List<DocumentDto> getAllDocumentsForSigning();

    void signDocument(Long documentId);

    void refuseSign(Long documentId);

    List<DocumentEntity> getAllDocuments();

    void sendToReviser(ChooseReviserDto choosereviserdto);

    String setCorrect(DocumentDto documentDto);

    String setVerified(DocumentDto documentDto);

    DocumentType categorizeDocument(String documentText);
}

package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;

import java.security.Principal;
import java.util.List;

public interface DocumentService {

    List<PhotoDocumentDto> getDocumentsByType(DocumentType documentType);

    String changeDocumentType(DocumentDto documentDto);

    List<DocumentEntity> getAllVerifiedDocuments();

    String setDocumentToBeSinged(DocumentDto documentDto);

    List<PhotoDocumentDto> getRevisionDocuments(Principal connectedEmployee);

    List<PhotoDocumentDto> getAllDocumentsForSigning();

    List<PhotoDocumentDto> getDocumentHistory(Principal connectedEmployee);

    String signDocument(DocumentDto documentDto);


    List<PhotoDocumentDto> getAllDocuments();

    void sendToReviser(ChooseReviserDto choosereviserdto);

    String setCorrect(DocumentDto documentDto);

    String setVerified(DocumentDto documentDto);

    DocumentType categorizeDocument(String documentText);


    List<PhotoDocumentDto> getAllUnconfirmedDocuments(Principal connectedEmployee);
}

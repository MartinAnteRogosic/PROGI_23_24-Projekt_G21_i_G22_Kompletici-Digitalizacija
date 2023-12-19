package hr.fer.progi.backend.service.impl;

import com.google.api.services.drive.model.File;
import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.scan.ImageProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionConfirmationService {

    private final DocumentRepository documentRepository;

    @Autowired
    public ConversionConfirmationService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public ImageProcessingResult processDocumentForConfirmation(Long documentId) {
        Document document = documentRepository.findById(documentId).orElse(null);
        File uploadedImagePath = null;
        File processedImagePath = null;
        boolean satisfied = true;
        return new ImageProcessingResult(uploadedImagePath, processedImagePath, satisfied);
    }

    public void confirmConversion(Long documentId, Boolean superVerified) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new DocumentNotFoundException(String.format("Document with id %d could not be found.", documentId)));

        document.setSuperVerified(superVerified);
        documentRepository.save(document);

    }
}

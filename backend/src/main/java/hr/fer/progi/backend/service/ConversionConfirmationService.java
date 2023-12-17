package hr.fer.progi.backend.service;

import hr.fer.progi.backend.entity.DocumentEntity;
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

    public ImageProcessingResult processDocumentForConfirmation(String documentId) {
        DocumentEntity document = documentRepository.findById(documentId).orElse(null);
        String uploadedImagePath = "";
        String processedImagePath = "";
        boolean satisfied = true;
        return new ImageProcessingResult(uploadedImagePath, processedImagePath, satisfied);
    }

    public void confirmConversion(String documentId, boolean superVerified) {
        DocumentEntity document = documentRepository.findById(documentId).orElse(null);
        if (document != null) {
            document.setSuperVerified(superVerified);
            documentRepository.save(document);
        }
    }
}

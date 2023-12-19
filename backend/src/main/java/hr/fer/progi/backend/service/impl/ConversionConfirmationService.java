package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.entity.Document;
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
        String uploadedImagePath = "";
        String processedImagePath = "";
        boolean satisfied = true;
        return new ImageProcessingResult(uploadedImagePath, processedImagePath, satisfied);
    }

    public void confirmConversion(Long documentId, boolean superVerified) {
        Document document = documentRepository.findById(documentId).orElse(null);
        if (document != null) {
            document.setSuperVerified(superVerified);
            documentRepository.save(document);
        }
    }
}

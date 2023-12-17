package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.service.ConversionConfirmationService;
import hr.fer.progi.backend.service.DocumentService;
import hr.fer.progi.backend.scan.ImageProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/confirmation")
public class ConversionConfirmationController {

    private final DocumentService documentService;
    private final ConversionConfirmationService conversionConfirmationService;

    @Autowired
    public ConversionConfirmationController(DocumentService documentService, ConversionConfirmationService conversionConfirmationService) {
        this.documentService = documentService;
        this.conversionConfirmationService = conversionConfirmationService;
    }

    @GetMapping("/documents/{documentType}")
    public List<DocumentEntity> getDocumentsForConfirmation(@PathVariable String documentType) {
        return documentService.getDocumentsByType(documentType);
    }

    @GetMapping("/document/{documentId}")
    public ImageProcessingResult getDocumentForConfirmation(@PathVariable String documentId) {
        return conversionConfirmationService.processDocumentForConfirmation(documentId);
    }

    @PostMapping("/confirm/{documentId}")
    public void confirmConversion(@PathVariable String documentId, @RequestBody boolean superVerified) {
        conversionConfirmationService.confirmConversion(documentId, superVerified);
    }
}

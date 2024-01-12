package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.service.impl.ConversionConfirmationService;
import hr.fer.progi.backend.scan.ImageProcessingResult;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/confirmation")
public class ConversionConfirmationController {

    private final DocumentServiceImpl documentService;
    private final ConversionConfirmationService conversionConfirmationService;


    @GetMapping("/documents/{documentType}")
    public List<DocumentDto> getDocumentsForConfirmation(@PathVariable DocumentType documentType) {
        return documentService.getDocumentsByType(documentType);
    }

    @GetMapping("/document/{documentId}")
    public ImageProcessingResult getDocumentForConfirmation(@PathVariable Long documentId) {
        return conversionConfirmationService.processDocumentForConfirmation(documentId);
    }

    @PostMapping("/confirm/{documentId}")
    public void confirmConversion(@PathVariable Long documentId, @RequestBody boolean superVerified) {
        conversionConfirmationService.confirmConversion(documentId, superVerified);
    }
}

package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/type/{documentType}")
    public List<DocumentEntity> getDocumentsByType(@PathVariable String documentType) {
        return documentService.getDocumentsByType(documentType);
    }

    @GetMapping("/{documentId}")
    public DocumentEntity getDocumentById(@PathVariable String documentId) {
        return documentService.getDocumentById(documentId);
    }
}

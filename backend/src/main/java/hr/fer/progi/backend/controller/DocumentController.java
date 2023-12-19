package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentServiceImpl documentService;


    @GetMapping("/type/{documentType}")
    public List<DocumentEntity> getDocumentsByType(@PathVariable String documentType) {
        return documentService.getDocumentsByType(documentType);
    }

    @GetMapping("/{documentId}")
    public DocumentEntity getDocumentById(@PathVariable String documentId) {
        return documentService.getDocumentById(documentId);
    }
}



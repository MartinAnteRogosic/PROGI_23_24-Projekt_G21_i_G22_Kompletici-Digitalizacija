package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentServiceImpl documentService;


    @GetMapping("/type/{documentType}")
    public List<Document> getDocumentsByType(@PathVariable DocumentType documentType) {
        return documentService.getDocumentsByType(documentType);
    }

    @GetMapping("/{documentId}")
    public Document getDocumentById(@PathVariable Long documentId) {
        return documentService.getDocumentById(documentId);
    }
}



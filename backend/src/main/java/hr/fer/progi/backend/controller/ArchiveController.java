package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    private final ArchiveService documentService;

    @Autowired
    public ArchiveController(ArchiveService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/archiveDocument")
    public String archiveDocument(@RequestParam String documentId) {
        try {
            documentService.archiveDocument(documentId);
            return "Document archived successfully";
        } catch (Exception e) {
            return "Error archiving document: " + e.getMessage();
        }
    }
}

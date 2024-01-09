package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.AllArchiveDocumentsDto;
import hr.fer.progi.backend.service.impl.ArchiveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/archive")
public class ArchiveController {

    private final ArchiveServiceImpl documentService;

    @PostMapping("/archiveDocument")
    public String archiveDocument(@RequestParam Long documentId) {
        try {
            documentService.archiveDocument(documentId);
            return "Document archived successfully";
        } catch (Exception e) {
            return "Error archiving document: " + e.getMessage();
        }
    }

    @GetMapping("/all-archive-documents")
    public ResponseEntity<?> getAllArchivedDocuments() {
        AllArchiveDocumentsDto allArchiveDocumentsDto = documentService.getAllArchivedDocuments();
        return new ResponseEntity<>(allArchiveDocumentsDto, HttpStatus.OK);
    }


}

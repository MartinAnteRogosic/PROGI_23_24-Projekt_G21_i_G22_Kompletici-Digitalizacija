package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.Photo;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
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

    @PostMapping("/choose-reviser")
    public ResponseEntity<String> chooseRevisor(@RequestBody ChooseRevisorDto chooserevisordto){
    return null;
    }

    @PostMapping("/historyDocument/{userId}")
    public ResponseEntity<List<Document>> getAllDocumentById(@PathVariable Long userId) {
        List<Document> documents = documentService.getAllDocumentsForUser(userId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
    @GetMapping("/historyDocument/{userId}/{documentId}")
    public ResponseEntity<Document> getDocumentAndPhotoById(@PathVariable Long documentId, @PathVariable String userId) {
        Document document = documentService.getDocumentById(documentId);
        Photo photo = documentService.getPhotoById(documentId);

//OVdje je potrebno vratiti i sliku - poslje
        if (document != null && photo != null) {
            return new ResponseEntity<>(document, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(document,HttpStatus.NOT_FOUND);
        }
    }
//u servisu ovo prebacitu entitet


package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.PhotoEntity;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public List<DocumentEntity> getDocumentsByType(@PathVariable DocumentType documentType) {
        return documentService.getDocumentsByType(documentType);
    }

    @GetMapping("/{documentId}")
    public DocumentEntity getDocumentById(@PathVariable Long documentId) {
        return documentService.getDocumentById(documentId);
    }

    @PostMapping("/choose-reviser")
    public ResponseEntity<String> chooseRevisor(@RequestBody ChooseReviserDto chooserevisordto) {
        return null;
    }

    @PostMapping("/historyDocument/{userId}")
    public ResponseEntity<List<DocumentEntity>> getAllDocumentById(@PathVariable Long userId) {
        List<DocumentEntity> documentEntities = documentService.getAllDocumentsForUser(userId);
        return new ResponseEntity<>(documentEntities, HttpStatus.OK);
    }

    @GetMapping("/historyDocument/{userId}/{documentId}")
    public ResponseEntity<DocumentEntity> getDocumentAndPhotoById(@PathVariable Long documentId, @PathVariable String userId) {
        DocumentEntity documentEntity = documentService.getDocumentById(documentId);
        PhotoEntity photo = documentService.getPhotoById(documentId);

        //OVdje je potrebno vratiti i sliku - poslje
        if (documentEntity != null && photo != null) {
            return new ResponseEntity<>(documentEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(documentEntity, HttpStatus.NOT_FOUND);
        }
        //u servisu ovo prebacitu entitet
    }

}

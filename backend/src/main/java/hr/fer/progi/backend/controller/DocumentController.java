package hr.fer.progi.backend.controller;
import hr.fer.progi.backend.dto.ChooseRevisorDto;
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
    public ResponseEntity<String> chooseRevisor(@RequestBody ChooseRevisorDto chooserevisordto) {
        return null;
    }

    @PostMapping("/historyDocument/{userId}")
    public ResponseEntity<List<DocumentEntity>> getAllDocumentById(@PathVariable Long userId) {
        List<DocumentEntity> documents = documentService.getAllDocumentsForUser(userId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/historyDocument/{userId}/{documentId}")
    public ResponseEntity<DocumentEntity> getDocumentAndPhotoById(@PathVariable Long documentId, @PathVariable String userId) {
        DocumentEntity document = documentService.getDocumentById(documentId);
        PhotoEntity photo = documentService.getPhotoById(documentId);

//OVdje je potrebno vratiti i sliku - poslje
        if (document != null && photo != null) {
            return new ResponseEntity<>(document, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(document, HttpStatus.NOT_FOUND);
        }
    }
//u servisu ovo prebacitu entitet

    //UC11
    //Svi dokumenti koji će se prikazivati računovođi (uvjet: verifed = TRUE)
    @GetMapping("/all-verified-documents")
    public ResponseEntity<List<DocumentEntity>> getAllVerifedDocuments() {

        List<DocumentEntity> listOfVerifedDocuments = documentService.getAllVerifedDocuments();
        return new ResponseEntity<>(listOfVerifedDocuments, HttpStatus.OK);
    }

    @GetMapping("/send-on-sign")
    public ResponseEntity<String> setDocumentsToBeSinged(Long documentId) {
        return documentService.setDocumentToBeSinged(documentId);
    }

    @GetMapping("/documents-for-sign")
    public ResponseEntity<List<DocumentEntity>> getAllDocumentsForSinged() {
        List<DocumentEntity> listOfDocumentsForSign = documentService.getAllDocumentsForSign();
        return new ResponseEntity<>(listOfDocumentsForSign, HttpStatus.OK);
    }

    @GetMapping("/sign-document")
    public ResponseEntity<String> signDocuments(@RequestParam Boolean confirm,Long documentId) {

        if(confirm == Boolean.TRUE){
            return documentService.signDocument(documentId);
        }else{
            return documentService.refuseSign(documentId);
        }
    }

}
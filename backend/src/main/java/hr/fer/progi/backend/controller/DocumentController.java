package hr.fer.progi.backend.controller;
import hr.fer.progi.backend.dto.ChooseRevisorDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.PhotoEntity;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hr.fer.progi.backend.service.impl.ImageServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentServiceImpl documentService;
    private final ImageServiceImpl imageService;
    private final DocumentRepository documentRepository;

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
    public ResponseEntity<PhotoDocumentDto> getDocumentAndPhotoById(@PathVariable Long documentId, @PathVariable String userId) {
        DocumentEntity document = documentService.getDocumentById(documentId);
        PhotoEntity photo = documentService.getPhotoById(documentId);
        PhotoDocumentDto dto = PhotoDocumentDto.builder()
                .document(document)
                .photo(photo)
                .build();

        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-verified-documents")
    public ResponseEntity<List<DocumentEntity>> getAllVerifedDocuments() {

        List<DocumentEntity> listOfVerifedDocuments = documentService.getAllVerifedDocuments();
        return new ResponseEntity<>(listOfVerifedDocuments, HttpStatus.OK);
    }

    @GetMapping("/send-on-sign")
    public ResponseEntity<String> setDocumentsToBeSinged(Long documentId) {
        documentService.setDocumentToBeSinged(documentId);
         return ResponseEntity.ok("Document successfully marked to be signed.");

    }

    @GetMapping("/documents-for-sign")
    public ResponseEntity<List<DocumentEntity>> getAllDocumentsForSinged() {
        List<DocumentEntity> listOfDocumentsForSign = documentService.getAllDocumentsForSign();
        return new ResponseEntity<>(listOfDocumentsForSign, HttpStatus.OK);
    }

    @GetMapping("/sign-document")
    public ResponseEntity<String> signDocuments(@RequestParam Boolean confirm,Long documentId) {

        if(confirm == Boolean.TRUE){
             documentService.signDocument(documentId);
            return ResponseEntity.ok("Document successfully marked to be signed.");
        }else{
            return ResponseEntity.ok("The document was rejected for signing.");
        }
    }


    @GetMapping("/all-documents")
    public ResponseEntity<List<PhotoDocumentDto>> getAllDocuments(){

        List<PhotoEntity> allPhotos = imageService.getAllPhotos();

        List<PhotoDocumentDto> dtoList = allPhotos.stream().map(photo ->{
            DocumentEntity document = documentRepository.findByPhoto(photo).orElseThrow(()-> new DocumentNotFoundException("not found"));

            PhotoDocumentDto dto = PhotoDocumentDto.builder()
                    .document(document)
                    .photo(photo)
                    .build();
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(dtoList,HttpStatus.OK);
    }

}
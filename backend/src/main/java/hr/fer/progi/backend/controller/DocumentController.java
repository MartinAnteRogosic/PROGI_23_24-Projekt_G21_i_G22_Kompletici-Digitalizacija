package hr.fer.progi.backend.controller;
import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/document")
public class DocumentController {

    private final DocumentServiceImpl documentService;
    private final ImageServiceImpl imageService;
    private final DocumentRepository documentRepository;

    @GetMapping("/type/{documentType}")
    public List<DocumentDto> getDocumentsByType(@PathVariable DocumentType documentType) {
        return documentService.getDocumentsByType(documentType);
    }

    @GetMapping("/{documentId}")
    public DocumentEntity getDocumentById(@PathVariable Long documentId) {
        return documentService.getDocumentById(documentId);
    }

    @GetMapping("/get-by-reviser-id/{reviserId}")
    public ResponseEntity<List<DocumentDto>>getDocumentsByReviserId(@PathVariable Long reviserId) {

        List<DocumentDto> documents = documentService.getDocumentsByVerificationEmployeeId(reviserId);

        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PostMapping("/send-to-reviser")
    public ResponseEntity<String> sendToReviser(@RequestBody ChooseReviserDto choosereviserdto) {

        documentService.sendToReviser(choosereviserdto);

        return new ResponseEntity<>("Document sent to reviser", HttpStatus.OK);
    }

    @PostMapping("/correct")
    public ResponseEntity<String> setCorrect(@RequestBody DocumentDto documentDto) {

        String response = documentService.setCorrect(documentDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> setValidate(@RequestBody DocumentDto documentDto) {

        String response = documentService.setVerified(documentDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
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

        List<DocumentEntity> listOfVerifiedDocuments = documentService.getAllVerifiedDocuments();
        return new ResponseEntity<>(listOfVerifiedDocuments, HttpStatus.OK);
    }

    @PostMapping("/send-to-sign")
    public ResponseEntity<String> setDocumentsToBeSinged(@RequestBody DocumentDto documentDto) {

        String response = documentService.setDocumentToBeSinged(documentDto);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/documents-for-sign")
    public ResponseEntity<List<DocumentDto>> getAllDocumentsForSigning(){
        List<DocumentDto> listOfDocumentsForSigning = documentService.getAllDocumentsForSigning();
        return new ResponseEntity<>(listOfDocumentsForSigning, HttpStatus.OK);
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
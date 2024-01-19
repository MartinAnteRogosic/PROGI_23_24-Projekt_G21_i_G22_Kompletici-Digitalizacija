package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/document")
public class DocumentController {

    private final DocumentServiceImpl documentService;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    @PostMapping("/get-by-type")
    public ResponseEntity<List<PhotoDocumentDto>> getDocumentsByType(@RequestBody DocumentDto documentDto) {
        List<PhotoDocumentDto> documents = documentService.getDocumentsByType(documentDto.getType());

        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/{documentId}")
    public DocumentEntity getDocumentById(@PathVariable Long documentId) {
        return documentService.getDocumentById(documentId);
    }

    @PostMapping("/change-category")
    public ResponseEntity<String> changeDocumentCategory(@RequestBody DocumentDto documentDto) {
        String  response = documentService.changeDocumentType(documentDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @GetMapping("/get-revision-documents")
    public ResponseEntity<?> getRevisionDocuments(Principal connectedEmployee){
        List<PhotoDocumentDto> listOfRevisionDocuments = documentService.getRevisionDocuments(connectedEmployee);

        return new ResponseEntity<>(listOfRevisionDocuments, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> setVerified(@RequestBody DocumentDto documentDto) {

        String response = documentService.setVerified(documentDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/document-history")
    public ResponseEntity<List<PhotoDocumentDto>> getAllDocumentHistory(Principal connectedEmployee) {

        List<PhotoDocumentDto> documents = documentService.getDocumentHistory(connectedEmployee);

        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<PhotoDocumentDto>> getDocumentAndPhotoById(Principal connectedEmployee) {

        List<PhotoDocumentDto> documents = documentService.getDocumentHistory(connectedEmployee);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/all-verified-documents")
    public ResponseEntity<List<DocumentEntity>> getAllVerifiedDocuments() {

        List<DocumentEntity> listOfVerifiedDocuments = documentService.getAllVerifiedDocuments();
        return new ResponseEntity<>(listOfVerifiedDocuments, HttpStatus.OK);
    }


    @PostMapping("/send-to-sign")
    public ResponseEntity<String> setDocumentsToBeSinged(@RequestBody DocumentDto documentDto) {
        String response = documentService.setDocumentToBeSinged(documentDto);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/documents-for-sign")
    public ResponseEntity<List<PhotoDocumentDto>> getAllDocumentsForSigning(){
        List<PhotoDocumentDto> listOfDocumentsForSigning = documentService.getAllDocumentsForSigning();
        return new ResponseEntity<>(listOfDocumentsForSigning, HttpStatus.OK);
    }


    @PostMapping("/sign-document")
    public ResponseEntity<String> signDocuments(@RequestBody DocumentDto documentDto) {
        String response = documentService.signDocument(documentDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/all-unconfirmed")
    public ResponseEntity<List<PhotoDocumentDto>> getAllUnconfirmedDocuments(Principal connectedEmployee){

        List<PhotoDocumentDto> listOfDocuments = documentService.getAllUnconfirmedDocuments(connectedEmployee);

        return new ResponseEntity<>(listOfDocuments, HttpStatus.OK);
    }



    @GetMapping("/all-documents")
    public ResponseEntity<List<PhotoDocumentDto>> getAllDocuments(){

        List<PhotoDocumentDto> listOfDocuments = documentService.getAllDocuments();

        return new ResponseEntity<>(listOfDocuments,HttpStatus.OK);
    }

}
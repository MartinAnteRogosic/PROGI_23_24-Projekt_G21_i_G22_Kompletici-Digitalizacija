package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.PhotoEntity;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CopyOnWriteArrayList;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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

    @GetMapping("/sse")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        return emitter;
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

    @GetMapping("/historyDocument/{userId}/{documentId}")
    public ResponseEntity<PhotoDocumentDto> getDocumentAndPhotoById(@PathVariable Long documentId, @PathVariable String userId) {
        DocumentEntity document = documentService.getDocumentById(documentId);
        PhotoEntity photo = document.getPhoto();
        PhotoDocumentDto dto = PhotoDocumentDto.builder()
                .documentId(document.getId())
                .documentName(document.getFileName())
                .documentUrl(document.getUrl())
                .photoId(photo.getPhotoID())
                .photoName(photo.getImageName())
                .photoUrl(photo.getUrl())
                .build();

        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-verified-documents")
    public ResponseEntity<List<DocumentEntity>> getAllVerifiedDocuments() {

        List<DocumentEntity> listOfVerifiedDocuments = documentService.getAllVerifiedDocuments();
        return new ResponseEntity<>(listOfVerifiedDocuments, HttpStatus.OK);
    }


    @PostMapping("/send-to-sign")
    public ResponseEntity<String> setDocumentsToBeSinged(@RequestBody DocumentDto documentDto) {
        for (SseEmitter emitter : this.emitters) {
            try {
                emitter.send(SseEmitter.event().name("message").data("Stigao je novi zahtjev za potpisivanje dokumenta!"));
            } catch (IOException e) {
                this.emitters.remove(emitter);
            }
        }

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
        for (SseEmitter emitter : this.emitters) {
            try {
                emitter.send(SseEmitter.event().name("message").data("Dokument je potpisan!"));
            } catch (IOException e) {
                this.emitters.remove(emitter);
            }
        }
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
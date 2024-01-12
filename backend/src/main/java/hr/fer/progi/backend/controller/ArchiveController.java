package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.AllArchiveDocumentsDto;
import hr.fer.progi.backend.dto.ArchiveDeleteDto;
import hr.fer.progi.backend.service.impl.ArchiveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/archive")
public class ArchiveController {

    private final ArchiveServiceImpl archiveService;

    @PostMapping("/archive-document/{documentId}")
    public ResponseEntity<String> archiveDocument(@PathVariable(name = "documentId") Long documentId) {
        String response = archiveService.archiveDocument(documentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all-archive-documents")
    public ResponseEntity<AllArchiveDocumentsDto> getAllArchivedDocuments() {
        AllArchiveDocumentsDto allArchiveDocumentsDto = archiveService.getAllArchivedDocuments();
        ResponseEntity<AllArchiveDocumentsDto> responseEntity = new ResponseEntity<>(allArchiveDocumentsDto, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/delete-document")
    public ResponseEntity<String> deleteDocument(@RequestBody ArchiveDeleteDto archiveDeleteDto) throws IOException {
        String response = archiveService.deleteDocument(archiveDeleteDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.ArchiveDeleteDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.service.impl.ArchiveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/archive")
public class ArchiveController {

    private final ArchiveServiceImpl archiveService;

    @PostMapping("/archive-document")
    public ResponseEntity<String> archiveDocument(@RequestBody DocumentDto documentDto) {
        String response = archiveService.archiveDocument(documentDto.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all-archive-documents")
    public ResponseEntity<List<PhotoDocumentDto>> getAllArchivedDocuments() {
        List<PhotoDocumentDto> allArchiveDocumentsDto = archiveService.getAllArchivedDocuments();
        return new ResponseEntity<>(allArchiveDocumentsDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete-document")
    public ResponseEntity<String> deleteDocument(@RequestBody ArchiveDeleteDto archiveDeleteDto) throws IOException {
        String response = archiveService.deleteDocument(archiveDeleteDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.repositroy.DocumentRepository;
import hr.fer.progi.backend.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController("/api/v1/document")
public class DocumentController {

    private final DocumentServiceImpl documentService;


    @GetMapping("/change-category")
    public ResponseEntity<?> getPhotoAndDocument(ChangeCategoryDto changeCategoryDto){
        ChangeCategoryDto response = documentService.getPhotoAndDocument(changeCategoryDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change-category")
    public ResponseEntity<?> changeDocumentCategory(ChangeCategoryDto changeCategoryDto){

        return  new ResponseEntity<>(documentService.changeCategory(changeCategoryDto), HttpStatus.OK);
    }
}

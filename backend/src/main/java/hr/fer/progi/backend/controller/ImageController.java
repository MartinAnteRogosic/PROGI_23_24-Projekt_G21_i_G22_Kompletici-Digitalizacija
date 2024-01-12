package hr.fer.progi.backend.controller;


import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.dto.UploadResponseDto;
import hr.fer.progi.backend.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageServiceImpl imageService;

    /*ovo je sam testno, nece ovak radit*/
    @PostMapping("/upload")
    public ResponseEntity<List<UploadResponseDto>> uploadImage(@RequestParam("files")List<MultipartFile> multipartFiles, Principal connectedEmployee) throws IOException {

        List<UploadResponseDto> response = imageService.processImages(multipartFiles, connectedEmployee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*ovo je sam testno, nece ovak radit*/
    @DeleteMapping("/delete/{imageId}")
    public  ResponseEntity<?> deleteImage(@PathVariable("imageId") Long imageId) throws IOException {
        String response = imageService.deleteImage(imageId);
        return ResponseEntity.ok(response);
    }
}

package hr.fer.progi.backend.controller;


import hr.fer.progi.backend.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageServiceImpl imageService;

    /*ovo je sam testno, nece ovak radit*/
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile multipartFile, Principal connectedEmployee) throws IOException {

        String response = imageService.processImage(multipartFile, connectedEmployee);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/uploadMultiple")
    public ResponseEntity<?> uploadMultipleImages(@RequestParam("files") List<MultipartFile> multipartFiles, Principal connectedEmployee) throws IOException {
        String response = imageService.processMultipleImages(multipartFiles, connectedEmployee);
        return ResponseEntity.ok(response);
    }

    /*ovo je sam testno, nece ovak radit*/
    @DeleteMapping("/delete/{imageId}")
    public  ResponseEntity<?> deleteImage(@PathVariable("imageId") Long imageId) throws IOException {
        String response = imageService.delete(imageId);
        return ResponseEntity.ok(response);
    }
}
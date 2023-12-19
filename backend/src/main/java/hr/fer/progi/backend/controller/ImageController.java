package hr.fer.progi.backend.controller;


import hr.fer.progi.backend.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageServiceImpl imageService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile multipartFile, Principal connectedEmployee){

        String response = imageService.upload(multipartFile, connectedEmployee);
        return ResponseEntity.ok(response);
    }
}

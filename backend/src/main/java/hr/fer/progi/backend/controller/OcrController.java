package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.service.impl.TesseractOCRServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
public class OcrController {

    private final TesseractOCRServiceImpl tesseractOCRServiceImpl;

    @PostMapping("/ocr")
    public String recognizeText(@RequestParam("file")MultipartFile file) throws IOException{
        return tesseractOCRServiceImpl.recognizeText(file.getInputStream());
    }
}


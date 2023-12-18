package hr.fer.progi.backend.controller;

import hr.fer.progi.backend.scan.ImageProcessingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import hr.fer.progi.backend.service.GoogleDriveService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static hr.fer.progi.backend.scan.ScanedImage.processImg;

@RestController
@RequestMapping("/scan")
class ScanedImageController {

    private GoogleDriveService googleDriveService;
    @PostMapping("/process")
    public ResponseEntity<String> processImages(@RequestParam("file") File file) {
        //it receives a list of files so that user can add multiple choice of files
        try {

            BufferedImage ipimage = ImageIO.read(file);
            File output = null;

            double d = ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2);

            if (d >= -1.4211511E7 && d < -7254228) {
                output = processImg(ipimage, 3f, -10f);
            } else if (d >= -7254228 && d < -2171170) {
                output = processImg(ipimage, 1.455f, -47f);
            } else if (d >= -2171170 && d < -1907998) {
                output = processImg(ipimage, 1.35f, -10f);
            } else if (d >= -1907998 && d < -257) {
                output = processImg(ipimage, 1.19f, 0.5f);
            } else if (d >= -257 && d < -1) {
                output = processImg(ipimage, 1f, 0.5f);
            } else if (d >= -1 && d < 2) {
                output = processImg(ipimage, 1f, 0.35f);
            }

            try {
                GoogleDriveService.uploadImage(ipimage);
                googleDriveService.uploadFile(output);

                return ResponseEntity.ok("Proccessing was successful");
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Unexpected error occurred.");
            }


        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Image not found or cannot be read.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected error occurred.");
        }

    }

    @PostMapping("/saveDecision")
    public ResponseEntity<String> saveUserDecision(@RequestBody File processedImage, @RequestParam("satisfied") boolean satisfied) {
        try {

            if (!satisfied) {
                File processedImageFile = processedImage;
                processedImageFile.delete();
            }
            else{
                GoogleDriveService.uploadFile(processedImage);
            }

            return ResponseEntity.ok("Decision saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving decision.");
        }
    }


}

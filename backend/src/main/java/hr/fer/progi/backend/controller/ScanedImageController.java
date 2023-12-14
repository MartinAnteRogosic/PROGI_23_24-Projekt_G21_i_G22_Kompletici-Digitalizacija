package hr.fer.progi.backend.controller;
import hr.fer.progi.backend.scan.ImageProcessingResult;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static hr.fer.progi.backend.scan.ScanedImage.processImg;

@RestController
@RequestMapping("/scan")
class ScanedImageController {

    @PostMapping("/process")
    public ResponseEntity<String> processImages(@RequestParam("filePaths") List<String> filePaths) {
        //it receives a list of files so that user can add multiple choice of files
        try {
            for (String filePath : filePaths) {
                File f = new File(filePath);
                BufferedImage ipimage = ImageIO.read(f);

                double d = ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2);

                if (d >= -1.4211511E7 && d < -7254228) {
                    processImg(ipimage, 3f, -10f);
                } else if (d >= -7254228 && d < -2171170) {
                    processImg(ipimage, 1.455f, -47f);
                } else if (d >= -2171170 && d < -1907998) {
                    processImg(ipimage, 1.35f, -10f);
                } else if (d >= -1907998 && d < -257) {
                    processImg(ipimage, 1.19f, 0.5f);
                } else if (d >= -257 && d < -1) {
                    processImg(ipimage, 1f, 0.5f);
                } else if (d >= -1 && d < 2) {
                    processImg(ipimage, 1f, 0.35f);
                }

                ImageProcessingResult response = new ImageProcessingResult();
                response.setUploadedImagePath(filePath);
                response.setProcessedImagePath("path/to/processed/image");

                return ResponseEntity.ok("Proccessing was successful");
            }


        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Image not found or cannot be read.");
        } catch (TesseractException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error during OCR processing.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected error occurred.");
        }

        return ResponseEntity.ok("Processing was successful");
    }

    @PostMapping("/saveDecision")
    public ResponseEntity<String> saveUserDecision(@RequestParam("processedImagePath") String processedImagePath, @RequestParam("satisfied") boolean satisfied) {
        try {

            if (!satisfied) {
                File processedImageFile = new File(processedImagePath);
                processedImageFile.delete();
            }

            return ResponseEntity.ok("Decision saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving decision.");
        }
    }




}

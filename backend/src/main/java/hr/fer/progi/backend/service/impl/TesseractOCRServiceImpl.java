package hr.fer.progi.backend.service.impl;


import hr.fer.progi.backend.service.TesseractService;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class TesseractOCRServiceImpl implements TesseractService {

    private final Tesseract tesseract;

    @Override
    public String recognizeText(InputStream inputStream) throws IOException{
        BufferedImage image = ImageIO.read(inputStream);

        try {
            return tesseract.doOCR(image);
        }catch (TesseractException exception){
            exception.printStackTrace();
        }
        return "failed";
    }
}

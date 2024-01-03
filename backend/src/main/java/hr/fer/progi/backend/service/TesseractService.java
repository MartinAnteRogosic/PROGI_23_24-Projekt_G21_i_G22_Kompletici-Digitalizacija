package hr.fer.progi.backend.service;


import java.io.IOException;
import java.io.InputStream;

public interface TesseractService {

    String recognizeText(InputStream inputStream) throws IOException;
}

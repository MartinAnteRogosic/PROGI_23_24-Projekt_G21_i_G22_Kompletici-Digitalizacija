package hr.fer.progi.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface CloudStorageService {

    String uploadFile(File file, String fileName) throws IOException;

    File convertToFile(MultipartFile multipartFile, String fileName) throws IOException;

    String getExtension(String fileName);
}

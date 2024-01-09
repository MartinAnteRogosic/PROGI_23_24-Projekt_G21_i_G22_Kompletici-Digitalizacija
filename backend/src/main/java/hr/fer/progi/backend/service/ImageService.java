package hr.fer.progi.backend.service;

import hr.fer.progi.backend.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

public interface ImageService {

    String uploadFile(File file, String fileName) throws IOException;

    File convertToFile(MultipartFile multipartFile, String fileName) throws IOException;

    String getExtension(String fileName);

    String upload(MultipartFile multipartFile, Principal connectedEmployee);

    String delete(Long imageId) throws IOException;

}

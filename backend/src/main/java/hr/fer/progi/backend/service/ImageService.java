package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.PhotoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ImageService {

    PhotoEntity uploadImage(MultipartFile multipartFile, EmployeeEntity employee);

    //String uploadDocument();

    String deleteImage(Long imageId) throws IOException;

    List<PhotoDocumentDto> processImages(List<MultipartFile> multipartFile, Principal connectedEmployee) throws IOException;

    List<PhotoEntity> getAllPhotos();
}

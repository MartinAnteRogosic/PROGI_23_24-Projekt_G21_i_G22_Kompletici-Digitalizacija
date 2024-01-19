package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.PhotoEntity;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.PhotoRepository;
import hr.fer.progi.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final PhotoRepository photoRepository;
    private final DocumentRepository documentRepository;
    private final TesseractOCRServiceImpl tesseractOCRService;
    private final CloudStorageServiceImpl cloudStorageService;
    private final DocumentServiceImpl documentService;



    @Override
    public PhotoEntity uploadImage(MultipartFile multipartFile, EmployeeEntity employee) {

        try {
            String fileName = Objects.requireNonNull(multipartFile.getOriginalFilename()).replaceAll(" ", "_");

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

            String fileName_new = fileName.split("\\.")[0].concat("_").concat(formatter.format(new Date())).concat(".").concat(fileName.split("\\.")[1]);

            File file = cloudStorageService.convertToFile(multipartFile, fileName_new);
            String URL = cloudStorageService.uploadFile(file, fileName_new);
            file.delete();


            PhotoEntity photo = PhotoEntity.builder()
                    .uploadEmployee(employee)
                    .imageName(fileName_new)
                    .url(URL)
                    .uploadTime(new Date())
                    .build();

            PhotoEntity savedPhoto = photoRepository.save(photo);

            return savedPhoto;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }

    @Override
    public String deleteImage(Long imageId) throws IOException {
        PhotoEntity photo = photoRepository.findById(imageId)
                .orElseThrow(() -> new PhotoNotFoundException("Photo could not be found"));

        return cloudStorageService.deleteFile(photo.getImageName());
    }

    @Override
    public List<PhotoDocumentDto> processImages(List<MultipartFile> multipartFiles, Principal connectedEmployee) throws IOException {

        EmployeeEntity employee = (EmployeeEntity) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();

        List<PhotoDocumentDto> listOfPhotoDocumentDto = multipartFiles.stream()
                .map(file ->{
                    try {
                        PhotoEntity photo = uploadImage(file, employee);

                        String documentText = tesseractOCRService.recognizeText(file.getInputStream());

                        File textFile = generateTextFile(documentText, photo.getImageName());
                        String documentURL = cloudStorageService.uploadFile(textFile, textFile.getName());
                        DocumentType documentType = documentService.categorizeDocument(documentText);

                        DocumentEntity document = DocumentEntity.builder()
                                .type(documentType)
                                .fileName(textFile.getName())
                                .url(documentURL)
                                .scanEmployee(employee)
                                .verified(false)
                                .signed(false)
                                .photo(photo)
                                .build();

                        DocumentEntity savedDocument = documentRepository.save(document);



                        return PhotoDocumentDto.builder()
                                .photoId(photo.getPhotoID())
                                .photoUrl(photo.getUrl())
                                .photoName(photo.getImageName())
                                .documentId(savedDocument.getId())
                                .documentUrl(savedDocument.getUrl())
                                .documentName(savedDocument.getFileName())
                                .documentType(savedDocument.getType())
                                .build();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }).collect(Collectors.toList());

        return listOfPhotoDocumentDto;
    }

    @Override
    public List<PhotoEntity> getAllPhotos() {
        return photoRepository.findAll();
    }

    public File generateTextFile(String documentText, String fileName) throws IOException {

        String documentName = fileName.split("\\.")[0];
        Path tempFile = Files.createTempFile(documentName, ".txt");
        Files.write(tempFile, documentText.getBytes(), StandardOpenOption.WRITE);
        return tempFile.toFile();

    }


}

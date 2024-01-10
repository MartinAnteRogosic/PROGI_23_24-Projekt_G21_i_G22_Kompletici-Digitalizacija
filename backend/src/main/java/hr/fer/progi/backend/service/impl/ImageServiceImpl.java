package hr.fer.progi.backend.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import hr.fer.progi.backend.entity.DocumentEntity;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final PhotoRepository photoRepository;
    private final DocumentRepository documentRepository;
    private final TesseractOCRServiceImpl tesseractOCRServiceImpl;

    @Override
    public String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("kompletici.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("media")
                .build();

        InputStream inputStream = ImageService.class
                .getClassLoader()
                .getResourceAsStream("kompletici-firebase-adminsdk-4g7dm-326a116887.json");

        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kompletici.appspot.com/o/%s?alt=media";

        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    @Override
    public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {

        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)){
            fos.write(multipartFile.getBytes());

        }
        return tempFile;
    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String uploadImage(MultipartFile multipartFile, EmployeeEntity employee) {

        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileName_new = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName_new);
            String URL = this.uploadFile(file, fileName_new);
            file.delete();


            PhotoEntity photo = PhotoEntity.builder()
                    .uploadEmployee(employee)
                    .imageName(fileName_new)
                    .url(URL)
                    .build();

            photoRepository.save(photo);

            return URL;
        } catch (Exception ex){
            ex.printStackTrace();
            return "Image could not be uploaded, something went wrong :(";
        }

    }

    /*ovo je sam testno, nece ovak radit*/
    @Override
    public String delete(Long imageId) throws IOException {
        PhotoEntity photo = photoRepository.findById(imageId)
                .orElseThrow(() -> new PhotoNotFoundException("Photo could not be found"));


        BlobId blobId = BlobId.of("kompletici.appspot.com", photo.getImageName());


        InputStream inputStream = ImageService.class
                .getClassLoader()
                .getResourceAsStream("kompletici-firebase-adminsdk-4g7dm-326a116887.json");

        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        storage.delete(blobId);

        return "Deleted successfully";
    }

    @Override
    public String processImage(MultipartFile multipartFile, Principal connectedEmployee) throws IOException {

        EmployeeEntity employee = (EmployeeEntity) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();
        String response = this.uploadImage(multipartFile, employee);
        String text = tesseractOCRServiceImpl.recognizeText(multipartFile.getInputStream());

        String documentName = UUID.randomUUID().toString();
        Path tempFile = Files.createTempFile(documentName, ".txt");
        Files.write(tempFile, text.getBytes(), StandardOpenOption.WRITE);

        File file = tempFile.toFile();

        String document_URL = this.uploadFile(file, documentName.concat(".txt"));

        DocumentEntity document = DocumentEntity.builder()
                .name(documentName)
                .url(document_URL)
                .scanEmployee(employee)
                .build();

        documentRepository.save(document);


        return "image processed successfully";
    }


}

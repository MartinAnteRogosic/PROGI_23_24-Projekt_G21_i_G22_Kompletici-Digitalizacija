package hr.fer.progi.backend.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import hr.fer.progi.backend.entity.Employee;
import hr.fer.progi.backend.entity.Photo;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.repository.PhotoRepository;
import hr.fer.progi.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final PhotoRepository photoRepository;

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
            fos.close();

        }
        return tempFile;
    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String upload(MultipartFile multipartFile, Principal connectedEmployee) {

        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName);
            String URL = this.uploadFile(file, fileName);
            file.delete();


            Employee employee = (Employee) ((UsernamePasswordAuthenticationToken)connectedEmployee).getPrincipal();

            Photo photo = Photo.builder()
                    .employee(employee)
                    .fileName(fileName)
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
        Photo photo = photoRepository.findById(imageId)
                .orElseThrow(() -> new PhotoNotFoundException("Photo could not be found"));


        BlobId blobId = BlobId.of("kompletici.appspot.com", photo.getFileName());


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



}

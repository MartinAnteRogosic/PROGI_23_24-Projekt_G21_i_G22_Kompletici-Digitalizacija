package hr.fer.progi.backend.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import hr.fer.progi.backend.service.CloudStorageService;
import hr.fer.progi.backend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


@Service
public class CloudStorageServiceImpl implements CloudStorageService {
    @Override
    public String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("kompletici.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("media")
                .build();

        InputStream inputStream = ImageService.class
                .getClassLoader()
                .getResourceAsStream("kompletici-firebase-adminsdk-4g7dm-326a116887.json");

        assert inputStream != null;
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        if (true){
            throw new PhotoNotFoundException("cetvri ex");
        }
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kompletici.appspot.com/o/%s?alt=media";

        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    @Override
    public String deleteFile(String fileName) throws IOException {
        BlobId blobId = BlobId.of("kompletici.appspot.com", fileName);


        InputStream inputStream = ImageService.class
                .getClassLoader()
                .getResourceAsStream("kompletici-firebase-adminsdk-4g7dm-326a116887.json");

        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        storage.delete(blobId);

        return "File deleted successfully";
    }

    @Override
    public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }

}

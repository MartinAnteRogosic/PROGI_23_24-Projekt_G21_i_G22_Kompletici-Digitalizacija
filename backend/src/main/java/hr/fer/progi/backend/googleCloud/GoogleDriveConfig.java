package hr.fer.progi.backend.googleCloud;
import org.springframework.beans.factory.annotation.Value;
import com.google.api.services.drive.Drive;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GoogleDriveConfig {

    /*@Value("${google.drive.credentials.location}")
    private String credentialsLocation;*/

    @Bean
    public Drive driveService() throws IOException {
        // Implement authentication logic and create a Drive service instance
        return null;
    }

}

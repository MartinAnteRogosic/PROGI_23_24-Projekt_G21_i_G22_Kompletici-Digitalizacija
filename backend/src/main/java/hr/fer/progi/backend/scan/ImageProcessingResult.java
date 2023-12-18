package hr.fer.progi.backend.scan;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

public class ImageProcessingResult {

    private File uploadedImagePath;
    private File processedImagePath;
    private boolean satisfied;

    public ImageProcessingResult(File uploadedImagePath, File processedImagePath, boolean satisfied) {
        this.uploadedImagePath = uploadedImagePath;
        this.processedImagePath = processedImagePath;
        this.satisfied = satisfied;
    }

    public ImageProcessingResult(){

    }

    public File getUploadedImagePath() {
        return uploadedImagePath;
    }

    public void setUploadedImagePath(File uploadedImagePath) {
        this.uploadedImagePath = uploadedImagePath;
    }

    public File getProcessedImagePath() {
        return processedImagePath;
    }

    public void setProcessedImagePath(File processedImagePath) {
        this.processedImagePath = processedImagePath;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }
}

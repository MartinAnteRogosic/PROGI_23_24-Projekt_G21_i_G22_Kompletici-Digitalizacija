package hr.fer.progi.backend.scan;

public class ImageProcessingResult {

    private String uploadedImagePath;
    private String processedImagePath;
    private boolean satisfied;

    public ImageProcessingResult(String uploadedImagePath, String processedImagePath, boolean satisfied) {
        this.uploadedImagePath = uploadedImagePath;
        this.processedImagePath = processedImagePath;
        this.satisfied = satisfied;
    }

    public ImageProcessingResult(){

    }

    public String getUploadedImagePath() {
        return uploadedImagePath;
    }

    public void setUploadedImagePath(String uploadedImagePath) {
        this.uploadedImagePath = uploadedImagePath;
    }

    public String getProcessedImagePath() {
        return processedImagePath;
    }

    public void setProcessedImagePath(String processedImagePath) {
        this.processedImagePath = processedImagePath;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }
}

package hr.fer.progi.backend.exception;

public class PhotoNotFoundException extends RuntimeException{

    private static final long serialVersionId = 3;

    public PhotoNotFoundException(String message){
        super(message);
    }
}

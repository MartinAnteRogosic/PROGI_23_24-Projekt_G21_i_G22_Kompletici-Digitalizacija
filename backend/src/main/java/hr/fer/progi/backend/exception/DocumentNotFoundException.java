package hr.fer.progi.backend.exception;

public class DocumentNotFoundException extends RuntimeException{

    private static final long serialVersionId = 4;

    public DocumentNotFoundException(String message){
        super(message);
    }
}

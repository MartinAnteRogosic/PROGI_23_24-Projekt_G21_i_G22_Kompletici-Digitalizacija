package hr.fer.progi.backend.exception;

public class RegistrationException extends RuntimeException{

    private static final long serialVersionId = 6;

    public RegistrationException(String message){
        super(message);
    }
}

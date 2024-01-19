package hr.fer.progi.backend.exception;

public class NoRevisersFoundException extends RuntimeException{

    private static final long serialVersionId = 5;

    public NoRevisersFoundException(String message){
        super(message);
    }
}

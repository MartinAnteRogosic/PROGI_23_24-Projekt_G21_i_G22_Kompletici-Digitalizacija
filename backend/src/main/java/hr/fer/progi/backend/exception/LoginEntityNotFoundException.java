package hr.fer.progi.backend.exception;

public class LoginEntityNotFoundException extends RuntimeException{


    private static final long serialVersionId = 1;
    public LoginEntityNotFoundException(String message) {
        super(message);
    }
}


package hr.fer.progi.backend.exception;

public class EmployeeNotFoundException extends RuntimeException {

    private static final long serialVersionId = 1;
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}

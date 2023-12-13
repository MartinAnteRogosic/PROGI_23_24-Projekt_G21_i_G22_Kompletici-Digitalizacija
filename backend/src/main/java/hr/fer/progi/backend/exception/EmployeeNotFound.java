package hr.fer.progi.backend.exception;

public class EmployeeNotFound extends RuntimeException {

    private static final long serialVersionId = 1;
    public EmployeeNotFound(String message) {
        super(message);
    }
}

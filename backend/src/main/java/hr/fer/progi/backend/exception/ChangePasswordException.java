package hr.fer.progi.backend.exception;

import hr.fer.progi.backend.dto.ChangePasswordRequestDto;

public class ChangePasswordException extends RuntimeException {
    private static final long serialVersionId = 2;

    public ChangePasswordException(String message) {
        super(message);
    }
}

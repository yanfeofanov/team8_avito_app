package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserForbiddenException extends RuntimeException {
    public UserForbiddenException(String message) {
        super(message);
    }
}

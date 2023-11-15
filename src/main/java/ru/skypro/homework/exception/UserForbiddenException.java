package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserForbiddenException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Пароли не должны совпадать";
    }
}


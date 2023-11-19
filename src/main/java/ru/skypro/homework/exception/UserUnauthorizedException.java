package ru.skypro.homework.exception;


public class UserUnauthorizedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Ошибка при авторизации";
    }
}


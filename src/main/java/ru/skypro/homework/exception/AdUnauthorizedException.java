package ru.skypro.homework.exception;

public class AdUnauthorizedException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Вам нужно авторизоваться";
    }
}

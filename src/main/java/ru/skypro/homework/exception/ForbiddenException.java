package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForbiddenException extends RuntimeException{
    private final String name;

    @Override
    public String getMessage() {
        return "Пользователь " + name + " не обладает правами доступа";
    }

}

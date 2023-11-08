package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdNullPointerException extends RuntimeException{

    private final int id;

    @Override
    public String getMessage() {
        return "Объявление с таким id: " + id + " не найдено";
    }
}

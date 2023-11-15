package ru.skypro.homework.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentNotFoundException extends RuntimeException {
    private final int adId;
    private final int commentId;

    @Override
    public String getMessage() {
        return "Не найден комментарий с идентификатором " + commentId + "к объявлению с идентификатором " + adId;
    }
}

package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto read(int idPk);

    CommentDto updateComments(int adId, int commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName);

    CommentDto addCommentById(int id, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName);

    void deleteCommentById(int adId, int commentId, String userName);

    void deleteAllCommentByPk(int adId);
}

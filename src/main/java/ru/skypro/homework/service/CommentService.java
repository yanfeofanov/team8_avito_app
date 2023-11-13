package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;

public interface CommentService {
    CommentsDto read(int idPk);

 //   CommentDto updateComments(int adId, int commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto);

   // CommentDto addCommentById(int id, CreateOrUpdateCommentDto createOrUpdateCommentDto);

   void deleteCommentById(int id, int commentId);
}

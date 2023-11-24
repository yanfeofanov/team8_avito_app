package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.CommentDtoMapper;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.AvitoUser;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final CommentDtoMapper commentDtoMapper;
    private final UserDetailsService userDetailsService;

    /**
     * Метод получения комментария по ID
     *
     * @param id уникальный идентификатор комментария
     * @return commentsDto
     */
    @Override
    public CommentsDto read(int id) {
        List<Comment> comment = commentRepository.findByAd_Pk(id);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comment.size());
        commentsDto.setResults(commentDtoMapper.findCommentsToDto(comment));
        return commentsDto;
    }

    /**
     * Метод редактирования комментария по ID объявления
     *
     * @param adId                     уникальный идентификатор объявления
     * @param commentId                уникальный идентификатор комментария
     * @param createOrUpdateCommentDto сущность для обновления комментария
     * @param userName                 никнейм пользователя
     * @return toDto
     */

    public CommentDto updateComments(int adId, int commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Comment comment = commentRepository.findCommentByAd_PkAndPk(adId, commentId);
        if (comment == null) {
            logger.warn("комментарий id=" + commentId + " для объявления id=" + adId + " не найден в БД");
            throw new CommentNotFoundException(adId, commentId);
        }
        if (user.getRole().equals(Role.ADMIN) || comment.getUser().getId() == user.getId()) {
            comment.setText(createOrUpdateCommentDto.getText());
            commentRepository.save(comment);
            logger.info("комментарий id=" + commentId + " для объявления id=" + adId + " успешно изменен");
        } else {
            logger.warn("у пользователя не достаточно прав для изменения комментария id=" + commentId + " к объявлению id=" + adId, comment);
            throw new ForbiddenException(userName);
        }
        return commentDtoMapper.toDto(comment);
    }

    /**
     * Метод удаления конкретного комментария юзера по ID объявления
     *
     * @param adId      уникальный идентификатор объявления
     * @param commentId уникальный идентификатор комментария
     * @param userName  никнейм пользователя
     */
    @Override
    public void deleteCommentById(int adId, int commentId, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Comment comment = commentRepository.findCommentByAd_PkAndPk(adId, commentId);
        if (comment == null) {
            logger.warn("комментарий id=" + commentId + " для объявления id=" + adId + " не найден в БД");
            throw new CommentNotFoundException(adId, commentId);
        }
        if (user.getRole().equals(Role.ADMIN) || comment.getUser().getId() == user.getId()) {
            commentRepository.delete(comment);
            logger.info("комментарий id=" + commentId + " для объявления id=" + adId + " успешно удален");
        } else {
            logger.warn("у пользователя не достаточно прав для удаления комментария id=" + commentId + " к объявлению id=" + adId, comment);
            throw new ForbiddenException(userName);
        }
    }

    /**
     * Метод удаления всех комментариев по ID объявления
     *
     * @param adId уникальный идентификатор объявления
     */

    public void deleteAllCommentByPk(int adId) {
        List<Comment> allCommentByPk = commentRepository.findByAd_Pk(adId);
        commentRepository.deleteAll(allCommentByPk);
        logger.warn("у объявления id=" + adId + " успешно удалены все комментарии");
    }


    /**
     * Метод добавления комментария по его ID
     *
     * @param adId                     уникальный идентификатор объявления
     * @param createOrUpdateCommentDto сущность для добавления комментария
     * @param userName                 никнейм пользователя
     * @return mapToCommentDto
     */
    public CommentDto addCommentById(int adId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Ad ad = adRepository.findByPk(adId);
        if (ad == null) {
            logger.warn("не найдено объявление id=" + adId);
            throw new AdNotFoundException(adId);
        }
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setAd(ad);
        comment.setText(createOrUpdateCommentDto.getText());
        comment.setCreatedAt(
                LocalDateTime.now()
                        .toInstant(ZoneOffset.UTC)
                        .toEpochMilli());
        commentRepository.save(comment);
        logger.info("успешно добавлен комментарий к объявлению id=" + adId);
        return commentDtoMapper.toDto(comment);
    }
}



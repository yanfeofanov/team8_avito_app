package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.CommentDtoMapper;
import ru.skypro.homework.Utils.MappingUtils;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdNullPointerException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.util.List;

import static liquibase.repackaged.net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final AdRepository adRepository;

    private final MappingUtils mappingUtils;

    private final UsersRepository usersRepository;


    @Override
    public CommentsDto read(int id) {
        List<Comment> comment = commentRepository.findByPk(id);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comment.size());
        commentsDto.setResults(CommentDtoMapper.INSTANCE.findCommentsToDto(comment));
        return commentsDto;

    }


    public CommentDto updateComments(int adId, int commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        Comment comment = commentRepository.findCommentByAd_PkAndPk(adId,commentId);
        comment.setText(createOrUpdateCommentDto.getText());
        commentRepository.save(comment);
        return CommentDtoMapper.INSTANCE.toDto(comment);
    }

    @Override
    public void deleteCommentById(int id, int commentId) {
        Comment comment = commentRepository.findCommentByAd_PkAndPk(id, commentId);
        commentRepository.delete(comment);
    }




    public CommentDto addCommentById(int id, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        Users user = usersRepository.findByEmail(userName);
        Comment comment = mappingUtils.mapToComment(1,createOrUpdateCommentDto,adRepository.findByPk(id),user);
        commentRepository.save(comment);
        return mappingUtils.mapToCommentDto(comment);
    }


}



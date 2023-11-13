package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.CommentDtoMapper;
import ru.skypro.homework.Utils.MappingUtils;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdForbiddenException;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final AdRepository adRepository;

    private final MappingUtils mappingUtils;

    private final UsersRepository usersRepository;

    private final UserDetailsService userDetailsService;

    @Override
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public CommentsDto read(int id) {
        List<Comment> comment = commentRepository.findByAd_Pk(id);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comment.size());
        commentsDto.setResults(CommentDtoMapper.INSTANCE.findCommentsToDto(comment));
        return commentsDto;
    }

    public CommentDto updateComments(int adId, int commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        Comment comment = commentRepository.findCommentByAd_PkAndPk(adId, commentId);
        Users user = (Users) userDetailsService.loadUserByUsername(userName);
        if (user.getRole().equals(Role.ADMIN) || comment.getUser().getId() == user.getId()) {
            comment.setText(createOrUpdateCommentDto.getText());
            commentRepository.save(comment);
        } else throw new AdForbiddenException(userName);
        return CommentDtoMapper.INSTANCE.toDto(comment);
    }

    @Override
    public void deleteCommentById(int id, int commentId, String userName) {
        Comment comment = commentRepository.findCommentByAd_PkAndPk(id, commentId);
        Users user = (Users) userDetailsService.loadUserByUsername(userName);
        if (user.getRole().equals(Role.ADMIN) || comment.getUser().getId() == user.getId()) {
            commentRepository.delete(comment);
        } else throw new AdForbiddenException(userName);
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public CommentDto addCommentById(int id, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        Users user = usersRepository.findByEmail(userName);
        Comment comment = mappingUtils.mapToComment(1, createOrUpdateCommentDto, adRepository.findByPk(id), user);
        commentRepository.save(comment);
        return mappingUtils.mapToCommentDto(comment);
    }
}



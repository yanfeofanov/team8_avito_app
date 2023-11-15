package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.CommentDtoMapper;
import ru.skypro.homework.Utils.MappingUtils;
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

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final MappingUtils mappingUtils;
    private final CommentDtoMapper commentDtoMapper;
    private final UserDetailsService userDetailsService;

    @Override
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public CommentsDto read(int id) {
        List<Comment> comment = commentRepository.findByAd_Pk(id);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comment.size());
        commentsDto.setResults(commentDtoMapper.findCommentsToDto(comment));
        return commentsDto;
    }

    public CommentDto updateComments(int adId, int commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Comment comment = commentRepository.findCommentByAd_PkAndPk(adId, commentId);
        if (comment == null) {
            throw new CommentNotFoundException(adId, commentId);
        }
        if (user.getRole().equals(Role.ADMIN) || comment.getUser().getId() == user.getId()) {
            comment.setText(createOrUpdateCommentDto.getText());
            commentRepository.save(comment);
        } else {
            throw new ForbiddenException(userName);
        }
        return commentDtoMapper.toDto(comment);
    }

    @Override
    public void deleteCommentById(int adId, int commentId, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Comment comment = commentRepository.findCommentByAd_PkAndPk(adId, commentId);
        if (comment == null) {
            throw new CommentNotFoundException(adId, commentId);
        }
        if (user.getRole().equals(Role.ADMIN) || comment.getUser().getId() == user.getId()) {
            commentRepository.delete(comment);
        } else {
            throw new ForbiddenException(userName);
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public CommentDto addCommentById(int adId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Ad ad = adRepository.findByPk(adId);
        if (ad == null) {
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
        return mappingUtils.mapToCommentDto(comment);
    }
}



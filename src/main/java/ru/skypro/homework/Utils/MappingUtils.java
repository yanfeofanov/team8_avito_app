package ru.skypro.homework.Utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Users;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingUtils {

    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(comment.getPk());
        commentDto.setText(comment.getText());
        commentDto.setAuthor(comment.getUser().getId());
        commentDto.setAuthorFirstName(comment.getUser().getFirstName());
        commentDto.setAuthorImage(comment.getUser().getImage());
        return commentDto;
    }

    public CommentsDto mapToCommentsDto(List<Comment> comments) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comments.size());
        commentsDto.setResults(comments.stream().map(this::mapToCommentDto).collect(Collectors.toList()));
        return commentsDto;
    }

    public Comment mapToComment(CommentDto commentDto, Ad ad, Users user) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAd(ad);
        comment.setUser(user);
        comment.setCreatedAt(commentDto.getCreatedAt());
        return comment;
    }

    public Comment mapToComment(int time, CreateOrUpdateCommentDto createOrUpdateCommentDto, Ad ad, Users user) {
        Comment comment = new Comment();
        comment.setText(createOrUpdateCommentDto.getText());
        comment.setAd(ad);
        comment.setUser(user);
        comment.setCreatedAt(time);
        return comment;

    }

    public UserDto mapToUserDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setImage(user.getImage());
        userDto.setLastName(user.getLastName());
        userDto.setFirstName(user.getFirstName());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public Users mapToUser(Register register, PasswordEncoder passwordEncoder) {
        Users users = new Users();
        users.setFirstName(register.getFirstName());
        users.setLastName(register.getLastName());
        users.setRole(register.getRole());
        users.setPhone(register.getPhone());
        users.setEmail(register.getUsername());
        users.setPassword(passwordEncoder.encode(register.getPassword()));
        return users;
    }

    public Users mapToUser(UpdateUserDto updateUserDto, Users user) {
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        return user;
    }
}

package ru.skypro.homework.Utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MappingUtils {

    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(comment.getPk());
        commentDto.setText(commentDto.getText());
        commentDto.setAuthor(comment.getUser().getId());
        commentDto.setAuthorFirstName(comment.getUser().getFirstName());
        commentDto.setAuthorImage(comment.getUser().getImage());
        return commentDto;
    }

    public CommentsDto mapToCommentsDto(Collection<Comment> comments) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comments.size());
        commentsDto.setResults(comments.stream().map(this::mapToCommentDto).collect(Collectors.toList()));
        return commentsDto;
    }

    public Comment mapToComment(CommentDto commentDto, Ad ad, User user) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAd(ad);
        comment.setUser(user);
        comment.setCreatedAt(commentDto.getCreatedAt());
        return comment;
    }

    public UserDto mapToUserDto(User user) {
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

    public User mapToUser(Register register) {
        User user = new User();
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setRole(register.getRole());
        user.setPhone(register.getPhone());
        //user.setEmail(register.getUsername());
        user.setUsername(register.getUsername());
        user.setPassword(register.getPassword());
        return user;
    }

    public User mapToUser(UpdateUserDto updateUserDto, User user) {
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        return user;
    }
}

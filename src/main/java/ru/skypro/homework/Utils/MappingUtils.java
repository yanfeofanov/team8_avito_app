package ru.skypro.homework.Utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Users;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingUtils {

    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(comment.getPk());
        commentDto.setText(comment.getText());
        commentDto.setAuthor(comment.getUsers().getId());
        commentDto.setAuthorFirstName(comment.getUsers().getFirstName());
        commentDto.setAuthorImage(comment.getUsers().getImage());
        return commentDto;
    }

    public CommentsDto mapToCommentsDto(List<Comment> comments) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comments.size());
        commentsDto.setResults(comments.stream().map(this::mapToCommentDto).collect(Collectors.toList()));
        return commentsDto;
    }

    public Comment mapToComment(CommentDto commentDto, Ad ad, Users users) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAd(ad);
        comment.setUsers(users);
        comment.setCreatedAt(commentDto.getCreatedAt());
        return comment;
    }

    public Comment mapToComment(int time, CreateOrUpdateCommentDto createOrUpdateCommentDto,Ad ad,Users user){
        Comment comment = new Comment();
        comment.setText(createOrUpdateCommentDto.getText());
        comment.setAd(ad);
        comment.setUsers(user);
        comment.setCreatedAt(time);
        return comment;

    }

    public UserDto mapToUserDto(Users users) {
        UserDto userDto = new UserDto();
        userDto.setId(users.getId());
        userDto.setEmail(users.getEmail());
        userDto.setPhone(users.getPhone());
        userDto.setImage(users.getImage());
        userDto.setLastName(users.getLastName());
        userDto.setFirstName(users.getFirstName());
        userDto.setRole(users.getRole());
        return userDto;
    }

    public Users mapToUser(Register register) {
        Users users = new Users();
        users.setFirstName(register.getFirstName());
        users.setLastName(register.getLastName());
        users.setRole(register.getRole());
        users.setPhone(register.getPhone());
        //user.setEmail(register.getUsername());
        users.setPassword(register.getPassword());
        return users;
    }

    public Users mapToUser(UpdateUserDto updateUserDto, Users users) {
        users.setFirstName(updateUserDto.getFirstName());
        users.setLastName(updateUserDto.getLastName());
        users.setPhone(updateUserDto.getPhone());
        return users;
    }
}

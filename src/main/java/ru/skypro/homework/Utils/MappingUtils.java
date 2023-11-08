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

    public AdDto mapToAdDto(Ad ad) {
        AdDto adDto = new AdDto();
        adDto.setPk(ad.getPk());
        adDto.setTitle(ad.getTitle());
        adDto.setPrice(ad.getPrice());
        adDto.setAuthor(adDto.getAuthor());
        adDto.setImage(adDto.getImage());
        return adDto;
    }

    public ExtendedAdDto mapToExtendedAdDto(Ad ad) {
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        extendedAdDto.setPk(ad.getPk());
        extendedAdDto.setAuthorFirstName(ad.getUser().getFirstName());
        extendedAdDto.setAuthorLastName(ad.getUser().getLastName());
        extendedAdDto.setDescription(ad.getDescription());
        extendedAdDto.setImage(ad.getImage());
        extendedAdDto.setEmail(ad.getUser().getEmail());
        extendedAdDto.setPhone(ad.getUser().getPhone());
        extendedAdDto.setTitle(ad.getTitle());
        extendedAdDto.setPrice(ad.getPrice());
        return extendedAdDto;
    }

    public AdsDto mapToAdsDto(Collection<Ad> ads) {
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(ads.stream().map(this::mapToAdDto).collect(Collectors.toList()));
        return adsDto;
    }

    public Ad mapToAd(CreateOrUpdateAdDto createOrUpdateAdDto, Ad ad) {
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        ad.setTitle(createOrUpdateAdDto.getTitle());
        return ad;
    }

    public Ad mapToAd(CreateOrUpdateAdDto createOrUpdateAdDto) {
        return mapToAd(createOrUpdateAdDto, new Ad());
    }

    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(comment.getPk());
        commentDto.setText(commentDto.getText());
        commentDto.setAuthor(comment.getUsers().getId());
        commentDto.setAuthorFirstName(comment.getUsers().getFirstName());
        commentDto.setAuthorImage(comment.getUsers().getImage());
        return commentDto;
    }

    public CommentsDto mapToCommentsDto(Collection<Comment> comments) {
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

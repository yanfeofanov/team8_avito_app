package ru.skypro.homework.Utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;

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

    public Ad mapToAd(AdDto adDto, User user) {
        Ad ad = new Ad();
        ad.setTitle(adDto.getTitle());
        ad.setDescription(adDto.getImage());
        //ad.setDescription();
        ad.setPrice(ad.getPrice());
        ad.setUser(user);
        return ad;
    }

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
        userDto.setRole(user.getRole().toString());
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

package ru.skypro.homework.Utils;

import io.swagger.v3.oas.annotations.media.Schema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Users;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Mapper
public interface CommentDtoMapper {

    CommentDtoMapper INSTANCE = Mappers.getMapper(CommentDtoMapper.class);


    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "author", source ="users.id" )
    @Mapping(target = "authorImage", source = "ad.image" )
    @Mapping(target = "authorFirstName", source = "users.firstName" )
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "text",source = "text")
    CommentDto toDto(Comment comment);

   List<CommentDto> findCommentsToDto(List<Comment> listComment);

    @Mapping(target = "pk", ignore = true)
    @Mapping(ignore = true, target ="users.id" )
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "text",source = "text")
    Comment toModel(CreateOrUpdateCommentDto createOrUpdateCommentDto);
}

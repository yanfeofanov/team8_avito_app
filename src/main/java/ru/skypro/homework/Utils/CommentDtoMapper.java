package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Mapper
public interface CommentDtoMapper {

    CommentDtoMapper INSTANCE = Mappers.getMapper(CommentDtoMapper.class);

    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "authorImage", source = "ad.image")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "text", source = "text")
    CommentDto toDto(Comment comment);

    List<CommentDto> findCommentsToDto(List<Comment> listComment);

    @Mapping(target = "pk", ignore = true)
    @Mapping(ignore = true, target = "user.id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "text", source = "text")
    Comment toModel(CreateOrUpdateCommentDto createOrUpdateCommentDto);
}

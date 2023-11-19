package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "authorImage", source = "user.image")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "createdAt", ignore = true)
    CommentDto toDto(Comment comment);

    List<CommentDto> findCommentsToDto(List<Comment> listComment);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "user.id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toModel(CreateOrUpdateCommentDto createOrUpdateCommentDto);
}

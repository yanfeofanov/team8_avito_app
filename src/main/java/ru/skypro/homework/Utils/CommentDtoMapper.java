package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "authorImage", source = "user.image")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    CommentDto toDto(Comment comment);
    List<CommentDto> findCommentsToDto(List<Comment> listComment);

}

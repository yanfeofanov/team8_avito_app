package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;

@Data
@Schema(description = "список комментариев к объявлению")
public class CommentsDto {
    @Schema(description = "количество комментариев к объявлению")
    private int count;
    @Schema(description = "список комментариев")
    private Collection<CommentDto> results;
}

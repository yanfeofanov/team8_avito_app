package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "список комментариев к объявлению")
public class CommentsDto {
    @Schema(description = "количество комментариев")
    private int count;
    @Schema(description = "сущности комментариев")

    private List<CommentDto> results;
}

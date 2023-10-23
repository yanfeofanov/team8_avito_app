package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "комментарий")
public class CommentDto {
    @Schema(description = "уникальный идентификатор комментария", accessMode = Schema.AccessMode.READ_ONLY)
    private int pk;
    @Schema(description = "уникальный идентификатор автора комментария")
    @NotEmpty
    private int author;
    @Schema(description = "ссылка на аватар автора комментария")
    private String authorImage;
    @Schema(description = "имя автора комментария")
    private String authorFirstName;
    @Schema(description = "дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    private long createdAt;
    @Schema(description = "текст комментария")
    private String text;
}

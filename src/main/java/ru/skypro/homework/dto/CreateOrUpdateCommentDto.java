package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "создание или обносление комментария")
public class CreateOrUpdateCommentDto {
    @Schema(description = "текст комментария")
    @NotBlank
    @Size(min = 8, max = 64)
    private String text;

}

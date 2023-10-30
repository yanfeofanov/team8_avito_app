package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "объявление")
public class AdDto {
    @Schema(description = "id автора объявления")
    @NotEmpty
    private int author;
    @Schema(description = "ссылка на картинку объявления")
    private String image;
    @Schema(description = "id объявления", accessMode = Schema.AccessMode.READ_ONLY)
    private int pk;
    @Schema(description = "цена объявления")
    private int price;
    @Schema(description = "заголовок объявления")
    @NotBlank
    private String title;
}

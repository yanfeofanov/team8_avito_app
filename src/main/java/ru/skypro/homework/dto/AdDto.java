package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdDto {
    @Schema(description = "id автора объявления")
    private int author;
    @Schema(description = "ссылка на картинку объявления")
    private String image;
    @Schema(description = "id объявления")
    private int pk;
    @Schema(description = "цена объявления")
    private int price;
    @Schema(description = "заголовок объявлени")
    private String title;
}

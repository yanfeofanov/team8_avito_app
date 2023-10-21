package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "расширенное представление объявления")
public class ExtendedAdDto {
    @Schema(description = "уникальный идентификатор объявления", accessMode = Schema.AccessMode.READ_ONLY)
    private int pk;
    @Schema(description = "имя автора объявления")
    private String authorFirstName;
    @Schema(description = "фамилия автора объявления")
    private String authorLastName;
    @Schema(description = "описание объявления")
    private String description;
    @Schema(description = "email автора объявления")
    private String email;
    @Schema(description = "ссылка на фото из объявления")
    private String image;
    @Schema(description = "номер телефона автора объявления")
    private String phone;
    @Schema(description = "стоимость из объявления")
    private int price;
    @Schema(description = "заголовок объявления")
    private String title;
}

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
@Schema(description = "создание или обносление объявления")
public class CreateOrUpdateAdDto {
    @Schema(description = "заголовок объявления")
    @NotBlank
    @Size(min = 4, max = 32)
    private String title;
    @Schema(description = "цена объявления")
    @Size(max = 10000000)
    private int price;
    @NotBlank
    @Size(min = 8, max = 64)
    @Schema(description = "описание объявления")
    private String description;
}

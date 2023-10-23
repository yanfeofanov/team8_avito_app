package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "сущность пользователь")
public class UserDto {
    @Schema(description = "уникальный идентификатор пользователя", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;
    @Schema(description = "email пользователя")
    private String email;
    @Schema(description = "имя пользователя")
    private String firstName;
    @Schema(description = "фамилия пользователя")
    private String lastName;
    @Schema(description = "номер телефона пользователя")
    private String phone;
    @NotBlank
    @Schema(description = "роль пользователя определяющая его права доступа")
    private String role;
    @Schema(description = "ссылка на аватар пользователя")
    private String image;
}

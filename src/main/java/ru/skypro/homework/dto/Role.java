package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "роли пользователей в системе")
public enum Role {
    USER, ADMIN
}

package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

@Schema(description = "роли пользователей в системе")
public enum Role {
    USER, ADMIN;
}

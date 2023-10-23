package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import javax.validation.Valid;

/**
 * класс содержит эндпойнты для работы с пользователеми и их данными
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "User controller", description = "контроллер для работы c пользователями")
@RequestMapping("/users")
public class UserController {
    @Operation(
            summary = "Обновление пароля пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NewPassword.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            }
    )
    @PostMapping("/set_password/{id}")
    public ResponseEntity<NewPassword> setPasswordForUser(@PathVariable (value = "id") int userId,
                                                          @RequestBody @Valid NewPassword newPassword) {
        return ResponseEntity.ok().build();
    }
    @Operation(
            summary = "Получение информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UserDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @GetMapping("/me/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable int id) {
        return ResponseEntity.ok().build();

    }
    @Operation(
            summary = "Обновление информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UpdateUserDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @PatchMapping("/me/{id}")
    public ResponseEntity<UpdateUserDto> updateUserInfo(@PathVariable int id, @RequestBody @Valid UpdateUserDto data) {
        return ResponseEntity.ok().build();

    }
    @Operation(
            summary = "Обновление аватара авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = {
                                    @Content(
                                    )
                            }
                    )

            }
    )
    @PatchMapping(value = "/me/avatar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> updateAvatar(@PathVariable int id, @RequestParam MultipartFile avatar) {
        return ResponseEntity.ok().build();
    }

}

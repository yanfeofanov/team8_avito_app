package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.UsersService;

import javax.validation.Valid;

/**
 * класс содержит эндпойнты для работы с пользователеми и их данными
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "User controller", description = "контроллер для работы c пользователями")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UsersService usersService;

    @Operation(
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
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
    @PostMapping("/set_password")
    public ResponseEntity<?> setPasswordForUser(@RequestBody @Valid NewPassword newPassword) {
        if (usersService.setPasswordForUser(newPassword)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        UserDto userDto = usersService.getUserInfo();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
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
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateUserInfo(@RequestBody @Valid UpdateUserDto data, Authentication authentication) {
        UpdateUserDto updateUserDto = usersService.updateUserData(data);
        return ResponseEntity.status(HttpStatus.OK).body(updateUserDto);
    }

    @Operation(
            summary = "Обновление аватара авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )

            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@RequestPart MultipartFile image) {
        return ResponseEntity.ok().build();
    }

}

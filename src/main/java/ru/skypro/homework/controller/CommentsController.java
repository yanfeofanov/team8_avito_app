package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * класс содержит эндпойнты для работы с комментариями пользователей к объявлениям
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Comments controller", description = "контроллер для работы с комментариями к объявлениям")
@Validated
@RequestMapping("ads")
public class CommentsController {
    @Operation(
            summary = "Получение комментариев объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommentsDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }
    )
    @GetMapping("{id}/comments")
    public CommentsDto getCommentsByAdId(@PathVariable @Parameter(description = "уникальный идентификатор объявления") int id) {
        return new CommentsDto();
    }

    @Operation(
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommentDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }
    )
    @PostMapping("{id}/comments")
    public CommentDto addCommentByAdId(@PathVariable @Parameter(description = "уникальный идентификатор объявления") int id,
                                       @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "текст комментария")
                                       @Size(min = 8, max = 64) String text) {
        return new CommentDto();
    }

    @Operation(
            summary = "Удаление комментария",
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }
    )
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteAdCommentById(@PathVariable @Parameter(description = "уникальный идентификатор объявления") int adId,
                                                 @PathVariable @Parameter(description = "уникальный идентификатор комментария") int commentId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommentDto.class)
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }
    )
    @PatchMapping("{adId}/comments/{commentId}")
    public CommentDto updateAdCommentById(@PathVariable @Parameter(description = "уникальный идентификатор объявления") int adId,
                                          @PathVariable @Parameter(description = "уникальный идентификатор комментария") int commentId,
                                          @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "текст комментария")
                                          @Size(min = 8, max = 64) String text) {
        return new CommentDto();
    }
}

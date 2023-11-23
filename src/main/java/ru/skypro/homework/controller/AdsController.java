package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Ads controller", description = "объявления")
@RequestMapping("ads")
public class AdsController {

    private final AdService adService;

    @Operation(
            summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdsDto.class)
                                    )
                            }
                    )
            }
    )

    @GetMapping()
    public ResponseEntity<AdsDto> getAllAds() {
        return new ResponseEntity<>(adService.getAllAds(), HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdDto.class)
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart @Valid CreateOrUpdateAdDto properties,
                                       @RequestPart(required = false) MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        return new ResponseEntity<>(adService.creatAd(properties, image, userEmail), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Получение информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ExtendedAdDto.class)
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = {
                                    @Content(
                                    )
                            }
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable int id) {
        return new ResponseEntity<>(adService.getExtendedAdDto(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content",
                            content = {
                                    @Content(
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
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = {
                                    @Content(
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = {
                                    @Content(
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAd(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        adService.deletedAd(id, userEmail);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновление информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdDto.class)
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
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = {
                                    @Content(
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = {
                                    @Content(
                                    )
                            }
                    )
            }
    )
    @PatchMapping("{id}")
    public ResponseEntity<AdDto> updateAds(@PathVariable int id,
                                           @RequestBody @Valid CreateOrUpdateAdDto properties) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        return new ResponseEntity<>(adService.updateAd(id, properties, userEmail), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdsDto.class)
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
    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        return new ResponseEntity<>(adService.getAdsDtoMe(userEmail), HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление картинки объявления",
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
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = {
                                    @Content(
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = {
                                    @Content(
                                    )
                            }
                    )
            }
    )
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateImage(@PathVariable int id, @RequestParam MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        adService.uploadImage(id, image, userEmail);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/get/{filename}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        return ResponseEntity.ok().body(adService.getAdImage(filename));
    }

}


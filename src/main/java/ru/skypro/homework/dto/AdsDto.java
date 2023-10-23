package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "список объявлений")
public class AdsDto {
    @Schema(description = "общее количество объявлений")
    private int count;
    @Schema(description = "сущности объявлений")
    private List<AdDto> results;
}

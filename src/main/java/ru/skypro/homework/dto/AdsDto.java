package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "список объявлений")
public class AdsDto {
    @Schema(description = "общее количество объявлений")
    private int count;
    @Schema(description = "сущности объявлений")
    private List<AdDto> results;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdsDto adsDto = (AdsDto) o;
        return count == adsDto.count && Objects.equals(results, adsDto.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, results);
    }
}

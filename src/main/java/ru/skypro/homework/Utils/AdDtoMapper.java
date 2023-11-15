package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdDtoMapper {

    @Mapping(target = "author", source = "user.id")
    AdDto toDto(Ad ad);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "description", ignore = true)
    Ad toAd(AdDto adDto);

    @Mapping(target = "author", source = "user.id")
    List<AdDto> adToAdsDtoList(List<Ad> adList);
}

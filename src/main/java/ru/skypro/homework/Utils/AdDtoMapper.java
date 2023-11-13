package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Mapper
public interface AdDtoMapper {

    AdDtoMapper INSTANCE = Mappers.getMapper(AdDtoMapper.class);

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    AdDto toDto(Ad ad);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "image", source = "image")
    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", ignore = true)
    Ad toAd(AdDto adDto);

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    List<AdDto> adToAdsDtoList(List<Ad> adList);
}

package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.model.Ad;

@Mapper
public interface CreateOrUpdateAdDtoMapper {

    CreateOrUpdateAdDtoMapper INSTANCE = Mappers.getMapper(CreateOrUpdateAdDtoMapper.class);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "description", source = "description")
    Ad creatDtoToAd(CreateOrUpdateAdDto createOrUpdateAdDto);
}

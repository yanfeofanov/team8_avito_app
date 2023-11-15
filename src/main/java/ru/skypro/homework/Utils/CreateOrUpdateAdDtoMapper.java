package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.model.Ad;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateAdDtoMapper {

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ad creatDtoToAd(CreateOrUpdateAdDto createOrUpdateAdDto);
}

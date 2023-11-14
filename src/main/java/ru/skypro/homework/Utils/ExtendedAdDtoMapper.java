package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;

@Mapper
public interface ExtendedAdDtoMapper {

    ExtendedAdDtoMapper INSTANCE = Mappers.getMapper(ExtendedAdDtoMapper.class);

    @Mapping(target = "pk", source = "ad.pk")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "image", source = "user.image")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    ExtendedAdDto adToExtendedDto(Ad ad);
}

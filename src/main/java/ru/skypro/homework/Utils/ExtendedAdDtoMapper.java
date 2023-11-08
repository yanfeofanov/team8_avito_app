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
    @Mapping(target = "authorFirstName", source = "users.firstName")
    @Mapping(target = "authorLastName", source = "users.lastName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "email", source = "users.email")
    @Mapping(target = "image", source = "users.image")
    @Mapping(target = "phone", source = "users.phone")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    ExtendedAdDto adToExtendedDto(Ad ad);
}

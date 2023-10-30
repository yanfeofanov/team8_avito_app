package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    AdDto toDto(Ad ad);

    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "image", source = "user.image")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    ExtendedAdDto adToExtendedDto(Ad ad);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "description", source ="description")
    Ad dtoToModel(CreateOrUpdateAdDto createOrUpdateAdDto);


    List<AdDto> adToAdsDtoList(List<Ad>adList);
}

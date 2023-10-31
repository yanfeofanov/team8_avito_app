package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Mapper
public interface AdMapper {

    AdMapper INSTANCE = Mappers.getMapper( AdMapper.class );

    @Mapping(target = "author", source = "users.id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    AdDto toDto(Ad ad);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "image", source = "image")
    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", ignore = true)
    Ad toAd(AdDto adDto);

    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "authorFirstName", source = "users.firstName")
    @Mapping(target = "authorLastName", source = "users.lastName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "email", source = "users.email")
    @Mapping(target = "image", source = "users.image")
    @Mapping(target = "phone", source = "users.phone")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    ExtendedAdDto adToExtendedDto(Ad ad);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "image", ignore = true)
    AdDto creatDtoToAdDto(CreateOrUpdateAdDto createOrUpdateAdDto);


    @Mapping(target = "author", source = "users.id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "pk", source = "pk")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    List<AdDto> adToAdsDtoList(List<Ad>adList);
}

package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.AvitoUser;

@Mapper
public interface UsersMapper {
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UserDto mapToUserDto(AvitoUser user);

    AvitoUser mapToUser(Register register);

    AvitoUser mapToUser(UpdateUserDto updateUserDto, @MappingTarget AvitoUser user);

    UpdateUserDto mapToUpdate(AvitoUser user);
}

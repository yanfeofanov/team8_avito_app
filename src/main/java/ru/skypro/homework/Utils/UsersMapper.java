package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.AvitoUser;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UserDto mapToUserDto(AvitoUser user);

    void mapToUser(UpdateUserDto updateUserDto, @MappingTarget AvitoUser user);

    UpdateUserDto mapToUpdate(AvitoUser user);
}

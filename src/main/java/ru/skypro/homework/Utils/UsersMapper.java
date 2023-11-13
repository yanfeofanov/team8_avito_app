package ru.skypro.homework.Utils;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Users;

@Mapper
public interface UsersMapper {
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UserDto mapToUserDto(Users user);

    Users mapToUser(Register register);

    Users mapToUser(UpdateUserDto updateUserDto, @MappingTarget Users user);

    UpdateUserDto mapToUpdate(Users user);
}

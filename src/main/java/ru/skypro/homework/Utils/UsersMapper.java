package ru.skypro.homework.Utils;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.AvitoUser;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UserDto mapToUserDto(AvitoUser user);


    AvitoUser mapToUser(UpdateUserDto updateUserDto, @MappingTarget AvitoUser user);

    UpdateUserDto mapToUpdate(AvitoUser user);

    @Mapping(source = "username", target = "email")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(register.getPassword()))")
    AvitoUser mapToRegister(Register register, @Context PasswordEncoder passwordEncoder);


}

package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.AvitoUser;

public interface UserService {

    boolean setPasswordForUser(NewPassword newPassword);
    UserDto getUserInfo();
    UpdateUserDto updateUserData(UpdateUserDto updateUserDto);

}

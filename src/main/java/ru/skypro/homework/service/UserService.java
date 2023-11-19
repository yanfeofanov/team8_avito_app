package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.AvitoUser;

public interface UserService {

    boolean setPasswordForUser(NewPassword newPassword);
    UserDto getUserInfo();
    UpdateUserDto updateUserData(UpdateUserDto updateUserDto);
    void uploadImageUsers(MultipartFile image, String userEmail);
    byte[] getUserImage(String filename);
}

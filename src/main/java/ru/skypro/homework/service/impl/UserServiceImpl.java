package ru.skypro.homework.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.Utils.UsersMapper;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.exception.UserUnauthorizedException;
import ru.skypro.homework.model.AvitoUser;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);
    private final UsersRepository usersRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UsersMapper usersMapper;
    @Getter
    @Value("${file.path.avatar}")
    private String filePath;


    /**
     * Метод изменения пароля пользователя
     *
     * @param newPassword новый пароль
     * @return boolean
     */
    public boolean setPasswordForUser(NewPassword newPassword) {
        AvitoUser user = getAuthUser();
        if (user == null) {
            return false;
        } else if (!encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            logger.warn("введенный пароль пользователя " + user.getUsername() + " не совпадает с паролем в БД", user);
            throw new UserUnauthorizedException();
        } else {
            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            usersRepository.save(user);
            logger.info("пароль пользователя " + user.getUsername() + " успешно изменен", user);
            return true;
        }
    }

    /**
     * Метод получения информации о пользователе
     *
     * @return UserDto
     */

    public UserDto getUserInfo() {
        AvitoUser user = getAuthUser();
        return usersMapper.mapToUserDto(user);
    }

    /**
     * Метод обновления информации о пользователе
     *
     * @param updateUserDto сущность для обновления информации пользователя
     * @return updateUserDto
     */

    public UpdateUserDto updateUserData(UpdateUserDto updateUserDto) {
        AvitoUser user = getAuthUser();
        usersMapper.mapToUser(updateUserDto, user);
        usersRepository.save(user);
        logger.info("данные пользователя " + user.getUsername() + " успешно обновлены", user);
        return usersMapper.mapToUpdate(user);
    }

    private AvitoUser getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        return (AvitoUser) userDetailsService.loadUserByUsername(currentUsername);
    }

    /**
     * Метод обновления аватрки пользователя
     *
     * @param image файл изображения для аватарки
     * @param userEmail никнейм пользователя
     */

    @Override
    public void uploadImageUsers(MultipartFile image, String userEmail) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userEmail);
        String dir = System.getProperty("user.dir") + "/" + filePath;
        try {
            Files.createDirectories(Path.of(dir));
            String fileName = String.format("avatar%s.%s", user.getEmail()
                    , StringUtils.getFilenameExtension(image.getOriginalFilename()));
            image.transferTo(new File(dir + "/" + fileName));
            user.setImage("/users/get/" + fileName);
            logger.info("изображение " + fileName + " для аватара пользователя, сохранено на сервере", image);
        } catch (IOException e) {
            logger.error("произошла ошибка при попытке сохранить изображение " + image.getOriginalFilename() + ", для аватара пользователя " + userEmail + ", на сервер", image);
            throw new RuntimeException(e);
        }
        usersRepository.save(user);

    }

    @Override
    public byte[] getUserImage(String filename) {
        try {
            return Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/" + getFilePath() + "/" + filename));
        } catch (IOException e) {
            logger.error("произошла ошибка при попытке прочитать изображение для аватара пользователя " + filename);
            throw new RuntimeException(e);
        }
    }
}

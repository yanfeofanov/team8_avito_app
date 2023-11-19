package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    private final UsersRepository usersRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UsersMapper usersMapper;
    @Value("${file.path.avatar}")
    private String filePath;


    public boolean setPasswordForUser(NewPassword newPassword) {
        AvitoUser user = getAuthUser();
        if (user == null) {
            return false;
        } else if (!encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            throw new UserUnauthorizedException();
        } else {
            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            usersRepository.save(user);
            return true;
        }
    }

    public UserDto getUserInfo() {
        AvitoUser user = getAuthUser();
        return usersMapper.mapToUserDto(user);
    }

    public UpdateUserDto updateUserData(UpdateUserDto updateUserDto) {
        AvitoUser user = getAuthUser();
        usersMapper.mapToUser(updateUserDto, user);
        usersRepository.save(user);
        return usersMapper.mapToUpdate(user);
    }

    private AvitoUser getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        return (AvitoUser) userDetailsService.loadUserByUsername(currentUsername);
    }

    @PreAuthorize("hasRole('USER')")
    public void uploadImageUsers(MultipartFile image, String userEmail) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userEmail);
        String dir = System.getProperty("user.dir") + "/" + filePath;
        try {
            Files.createDirectories(Path.of(dir));
            String fileName = String.format("avatar%s.%s", user.getEmail()
                                                           , getExtension(image.getOriginalFilename()));
            image.transferTo(new File(dir + "/" + fileName));
            user.setImage("/users/get/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        usersRepository.save(user);
    }
    /**
     * Метод указывает расширение файла
     * @return String
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Метод имя папки файла
     * @return String
     */
    public String getFilePath() {
        return filePath;
    }

    public byte[] getUserImage(String filename) {
        byte[] avatar = new byte[0];
        try {
            avatar = Files.readAllBytes(Paths.get(System.getProperty("user.dir") +"/"+getFilePath()+ "/" +filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return avatar;
    }
}

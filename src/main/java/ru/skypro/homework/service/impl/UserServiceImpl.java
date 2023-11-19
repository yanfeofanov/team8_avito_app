package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.UsersMapper;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.exception.UserUnauthorizedException;
import ru.skypro.homework.model.AvitoUser;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UsersMapper usersMapper;

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
}

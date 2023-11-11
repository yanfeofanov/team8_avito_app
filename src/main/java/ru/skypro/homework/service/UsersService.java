package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.UsersMapper;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.exception.UserForbiddenException;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.UsersRepository;

//@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    // private final UserDetailsManager manager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public UsersService(UsersRepository usersRepository, UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    public boolean setPasswordForUser(NewPassword newPassword) {
        Users user = getAuthUser();
        if (user == null) {
            return false;
        } else if (encoder.matches(newPassword.getCurrentPassword(), newPassword.getNewPassword())) {
            throw new UserForbiddenException("Пароли не должны совпадать");
        } else {
            //manager.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
            user.setPassword(newPassword.getNewPassword());
            usersRepository.save(user);
            return true;
        }
    }

    public UserDto getUserInfo() {
        Users user = getAuthUser();
       /* if (!manager.userExists(user.getEmail())) {
            throw new UserUnauthorizedException("Необходимо авторизоваться");
        }*/
        return UsersMapper.INSTANCE.mapToUserDto(user);
    }

    public UpdateUserDto updateUserData(UpdateUserDto updateUserDto) {
        Users user = getAuthUser();
        UsersMapper.INSTANCE.mapToUser(updateUserDto, user);
        usersRepository.save(user);
        return UsersMapper.INSTANCE.mapToUpdate(user);
    }

    private Users getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        //return usersRepository.findByEmail(currentUsername);
        return (Users) userDetailsService.loadUserByUsername(currentUsername);
    }
}

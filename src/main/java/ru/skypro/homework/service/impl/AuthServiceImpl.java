package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.MappingUtils;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final MappingUtils mappingUtils;
    private final UsersRepository usersRepository;

    public AuthServiceImpl(UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder, MappingUtils mappingUtils, UsersRepository usersRepository) {
        this.userDetailsService = userDetailsService;
        this.encoder = passwordEncoder;
        this.mappingUtils = mappingUtils;
        this.usersRepository = usersRepository;
    }

    /**
     * Метод логирования пользотваеля
     * @param userName
     * @param password
     * @return boolean
     */
    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    /**
     * Метод регистрации пользователя занесение данных в репозиторий
     * @param register
     * @return boolean
     */
    @Override
    public boolean register(Register register) {
        usersRepository.save(mappingUtils.mapToUser(register, encoder));
        return true;
    }
}

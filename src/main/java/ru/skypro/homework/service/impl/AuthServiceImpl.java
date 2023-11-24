package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.CommentDtoMapper;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final CommentDtoMapper commentDtoMapper;
    private final UsersRepository usersRepository;

    public AuthServiceImpl(UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder, CommentDtoMapper commentDtoMapper, UsersRepository usersRepository) {
        this.userDetailsService = userDetailsService;
        this.encoder = passwordEncoder;
        this.commentDtoMapper = commentDtoMapper;
        this.usersRepository = usersRepository;
    }

    /**
     * Метод логирования пользотваеля
     *
     * @param userName никнейм пользователя
     * @param password пароль
     * @return boolean
     */
    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        logger.info("успешная аутентификация пользователя " + userName, userDetails);
        return encoder.matches(password, userDetails.getPassword());
    }

    /**
     * Метод регистрации пользователя занесение данных в репозиторий
     *
     * @param register сущность для регистрации нового пользователя
     * @return boolean
     */
    @Override
    public boolean register(Register register) {
        register.setPassword(encoder.encode(register.getPassword()));
        usersRepository.save(commentDtoMapper.toAvitoUser(register));
        logger.info("успешная регистрация нового пользователя " + register.getUsername(), register);
        return true;
    }
}

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

    //private final UserDetailsManager manager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final MappingUtils mappingUtils;
    private final UsersRepository usersRepository;

    public AuthServiceImpl(//UserDetailsManager manager,
                           UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder, MappingUtils mappingUtils, UsersRepository usersRepository) {
        //this.manager = manager;
        this.userDetailsService = userDetailsService;
        this.encoder = passwordEncoder;
        this.mappingUtils = mappingUtils;
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean login(String userName, String password) {
       /* if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());*/
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
       /* if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())
                        .build());
        return true;*/
        usersRepository.save(mappingUtils.mapToUser(register, encoder));
        return true;
    }
}

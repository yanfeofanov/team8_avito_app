package ru.skypro.homework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.model.AvitoUser;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);

    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return username -> {
            AvitoUser user = usersRepository.findByEmail(username);
            if (user != null) {
                return user;
            }
            logger.warn("пользователь под username=" + username + " не найден в базе");
            throw new UsernameNotFoundException("User '" + username + "' не найден");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login"
                        , "/register"
                        , "/ads"
                        , "/ads/get/*"
                        , "/users/get/*").permitAll()
                .antMatchers("/ads/**", "/users/**").authenticated()
                .and()
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

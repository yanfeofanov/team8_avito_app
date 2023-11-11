package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.UsersRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

  /*  private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**"
    };*/

    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return username -> {
            Users user = usersRepository.findByEmail(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User '" + username + "' не найден");
        };
    }
    /*public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user =
                User.builder()
                        .username("user@gmail.com")
                        .password("password")
                        .passwordEncoder(passwordEncoder::encode)
                        .roles(Role.USER.name())
                        .build();
        return new InMemoryUserDetailsManager(user);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/ads/**", "/users/**").access("hasRole('USER') || hasRole('ADMIN')")
                //.antMatchers("/login", "/register", "/ads").permitAll()
                .anyRequest().permitAll()
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

package com.bank.authorization.service;

import com.bank.authorization.controllers.UserRestController;
import com.bank.authorization.entity.User;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.pojos.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Класс необходимый для аутентификации и авторизации в приложении
 */

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final UserRestController userRestController;
    @Autowired
    public UserDetailsServiceImpl(UserService userService, UserRestController userRestController) {
        this.userService = userService;
        this.userRestController = userRestController;
    }

    /**
     * Метод loadUserByUsername(String username) - подгружает пользователя из базы данных по полю email(username)
     * В случае если пользователь отсутствует - выбрасывает исключение UsernameNotFoundException
     * Загрузка email происходит получением его из микросервиса profile с помощью FeignClient
     * Загрузка password и roles происходит из базы данных данного микросервиса
     * Сопоставление email и password происходит по полю profileId сущности User
     *
     * @return возвращает объект UserDetails, включающий username, password, roles(authorities)
     * @author Vladislav Shilov
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Call loadUserByUsername() method in {}", this.getClass());
        final Optional<Profile> profile = Optional.ofNullable(userRestController.getProfileByUsername(username));
        if (profile.isEmpty()) {
            log.error("User with mail {} not found in {}", username, this.getClass());
            throw new UsernameNotFoundException(String.format("User with mail %s not found", username));
        } else {
            final User user = UserMapper.MAPPER.toUser(userService.getByProfileId(profile.get().getId()));
            log.info("Successful authorization");
            return new org.springframework.security.core.userdetails.User(profile.get().getEmail(),
                    user.getPassword(), user.getAuthorities());
        }
    }
}

package com.bank.authorization.util;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.service.RoleService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.bank.authorization.service.UserService;
import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс для инициализации баз данных user и roles, если они пустые
 * Добавляет две роли(Admin и User)
 * Добавляет двух пользователей(одного с ролью Admin, другого с ролью User)
 */

@Component
public class DatabaseInit {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public DatabaseInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initDbUsers() {

        final Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);
        final Role roleUser = new Role(RoleEnum.ROLE_USER);

        if (roleService.getAll().isEmpty()) {
            roleService.add(roleAdmin);
            roleService.add(roleUser);
        }

        if (userService.getAll().isEmpty()) {

            final Set<Role> adminRoles = new HashSet<>();
            Collections.addAll(adminRoles, roleService.getById(1L), roleService.getById(2L));
            final UserDTO admin = UserDTO.builder()
                    .role(adminRoles)
                    .profileId(1L)
                    .password("Admin1@")
                    .build();
            userService.add(admin);

            final Set<Role> userRoles = new HashSet<>();
            Collections.addAll(userRoles, roleService.getById(2L));
            final UserDTO user = UserDTO.builder()
                    .role(userRoles)
                    .profileId(2L)
                    .password("User1@")
                    .build();
            userService.add(user);
        }
    }
}

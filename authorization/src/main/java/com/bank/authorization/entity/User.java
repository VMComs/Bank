package com.bank.authorization.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Сущность User, имплементирующая UserDetails для секьюрности
 * Поля:
 * Long id - идентификатор пользователя
 * Set<Role> role - Коллекция ролей пользователя, связана Many-to-Many с сущностью Role с EAGER загрузкой(не критично)
 * Long profileId - идентификатор сущности Profile другого микросервиса(содержит основную информацию профиля и username)
 * String password - Пароль пользователя(валидация поля происходит паттерном регулярного выражения)
 * getAuthorities() - возвращает лист ролей пользователя
 * getUsername() - должен возвращать username, но логика предоставления username реализована в
 * UserRestController и UserDetailsServiceImpl с помощью ProfileFeignClient
 */

@Entity
@Table(name = "user", schema = "auth")
@Getter
@Setter
@Builder
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;

    @NotNull
    @NotEmpty(message = "ProfileId should not be empty")
    @Column(name = "profile_id")
    private Long profileId;

    @NotNull
    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{5,}$",
            message = "The string must be at least 5 characters long, contain at least 1 uppercase letter, " +
                    "1 lowercase letter, and OR special character OR number.")
    private String password;

    public User(Set<Role> role, Long profileId, String password) {
        this.role = role;
        this.profileId = profileId;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }

    @Override
    public String getUsername() {
        return null; // возвращается из сервиса profile
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

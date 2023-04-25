package com.bank.authorization.dto;

import com.bank.authorization.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;

/**
 * Трансферный слой dto для сущности user
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotNull
    private Long id;
    @NotNull
    @NotEmpty(message = "Role should not be empty")
    private Set<Role> role;

    @NotNull
    @NotEmpty(message = "ProfileId should not be empty")
    private Long profileId;

    @NotNull
    @NotEmpty(message = "Password should not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{5,}$",
            message = "The string must be at least 5 characters long, contain at least 1 uppercase letter, " +
                    "1 lowercase letter, and OR special character(@#$%) OR number.")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(role, userDTO.role) &&
                Objects.equals(profileId, userDTO.profileId) &&
                Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, profileId, password);
    }
}

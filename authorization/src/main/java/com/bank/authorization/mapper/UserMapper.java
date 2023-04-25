package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Маппер Mapstruct для сущности User
 * toUser - преобразует UserDTO в User
 * toUserDTO - преобразует User в UserDTO
 * Бизнес-логика преобразования реализуется в RestController
 */

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO userDTO);
    UserDTO toDTO(User user);
    List<UserDTO> toDTOList(List<User> userDTOList);
}

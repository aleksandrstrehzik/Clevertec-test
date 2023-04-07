package ru.clevertec.test.dto.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.test.dto.UserDto;
import ru.clevertec.test.repository.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User uer);
}

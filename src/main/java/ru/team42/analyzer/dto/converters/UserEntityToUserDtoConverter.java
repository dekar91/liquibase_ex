package ru.team42.analyzer.dto.converters;

import ru.team42.analyzer.dto.response.RoleDto;
import ru.team42.analyzer.dto.response.UserDto;
import ru.team42.analyzer.entities.UserEntity;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

public class UserEntityToUserDtoConverter implements org.springframework.core.convert.converter.Converter<UserEntity, UserDto> {
    @Override
    public UserDto convert(@NotNull UserEntity entity) {
        return new UserDto(entity.getId(), entity.getUsername(), null, entity.getRoles()
                .stream().map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet()), entity.getChat2DeskId()
        );
    }
}

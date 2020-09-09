package ru.team42.analyzer.dto.converters;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.team42.analyzer.dto.response.UserDto;
import ru.team42.analyzer.entities.RoleEntity;
import ru.team42.analyzer.entities.UserEntity;

import java.util.stream.Collectors;

public class UserDtoToUserEntityConverter implements Converter<UserDto, UserEntity> {
    @Override
    public UserEntity convert(@NotNull UserDto source) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(source, userEntity, "roles");
        if(source.getRoles() != null) {
            userEntity.setRoles(source.getRoles().stream().map(role -> RoleEntity.builder()
                    .name(role.getName())
                    .id(role.getId())
                    .build()).collect(Collectors.toSet()));
        }


        return userEntity;
    }
}

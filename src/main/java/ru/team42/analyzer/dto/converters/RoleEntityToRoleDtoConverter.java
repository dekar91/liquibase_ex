package ru.team42.analyzer.dto.converters;

import ru.team42.analyzer.dto.response.RoleDto;
import ru.team42.analyzer.entities.RoleEntity;

import javax.validation.constraints.NotNull;

public class RoleEntityToRoleDtoConverter implements org.springframework.core.convert.converter.Converter<RoleEntity, RoleDto> {
    @Override
    public RoleDto convert(@NotNull RoleEntity entity) {
        return new RoleDto(entity.getId(), entity.getName());
    }
}

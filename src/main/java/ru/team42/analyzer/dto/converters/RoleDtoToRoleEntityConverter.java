package ru.team42.analyzer.dto.converters;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.team42.analyzer.dto.response.RoleDto;
import ru.team42.analyzer.entities.RoleEntity;

public class RoleDtoToRoleEntityConverter implements Converter<RoleDto, RoleEntity> {
    @Override
    public RoleEntity convert(@NotNull RoleDto source) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(source, roleEntity);

        return roleEntity;
    }
}

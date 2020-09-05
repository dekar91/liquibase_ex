package ru.team42.analyzer.dto.converters;

import ru.team42.analyzer.dto.response.IntegrationDto;
import ru.team42.analyzer.entities.IntegrationEntity;

import javax.validation.constraints.NotNull;

public class IntegrationEntityToIntegrationDtoConverter implements org.springframework.core.convert.converter.Converter<IntegrationEntity, IntegrationDto> {
    @Override
    public IntegrationDto convert(@NotNull IntegrationEntity entity) {
        return new IntegrationDto(entity.getId(), entity.getName(), entity.getType(), entity.getSettings());
    }
}

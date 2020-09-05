package ru.team42.analyzer.dto.converters;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.team42.analyzer.dto.response.IntegrationDto;
import ru.team42.analyzer.entities.IntegrationEntity;

public class IntegrationDtoToIntegrationEntityConverter implements Converter<IntegrationDto, IntegrationEntity> {
    @Override
    public IntegrationEntity convert(@NotNull IntegrationDto source) {
        IntegrationEntity integrationEntity = new IntegrationEntity();
        BeanUtils.copyProperties(source, integrationEntity);

        return integrationEntity;
    }
}

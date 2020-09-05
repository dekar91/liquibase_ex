package ru.team42.analyzer.dto.converters;

import ru.team42.analyzer.dto.response.HitDto;
import ru.team42.analyzer.entities.HitEntity;

import javax.validation.constraints.NotNull;

public class HitEntityToHitDtoConverter implements org.springframework.core.convert.converter.Converter<HitEntity, HitDto> {
    @Override
    public HitDto convert(@NotNull HitEntity entity) {
        return new HitDto(entity.getId(), entity.getButtonId(), entity.getUrl(), entity.getAction(), entity.getData());
    }
}

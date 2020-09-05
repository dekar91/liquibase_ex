package ru.team42.analyzer.dto.converters;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.team42.analyzer.dto.response.HitDto;
import ru.team42.analyzer.entities.HitEntity;

public class HitDtoToHitEntityConverter implements Converter<HitDto, HitEntity> {
    @Override
    public HitEntity convert(@NotNull HitDto source) {
        HitEntity hitEntity = new HitEntity();
        BeanUtils.copyProperties(source, hitEntity);

        return hitEntity;
    }
}

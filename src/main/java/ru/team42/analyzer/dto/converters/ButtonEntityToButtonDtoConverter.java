package ru.team42.analyzer.dto.converters;

import ru.team42.analyzer.dto.response.ButtonDto;
import ru.team42.analyzer.entities.ButtonEntity;

import javax.validation.constraints.NotNull;

public class ButtonEntityToButtonDtoConverter implements org.springframework.core.convert.converter.Converter<ButtonEntity, ButtonDto> {
    @Override
    public ButtonDto convert(@NotNull ButtonEntity entity) {
        return new ButtonDto(entity.getId(), entity.getName(),
                entity.getChannelId(),
                entity.getMessengerId()
        );
    }
}

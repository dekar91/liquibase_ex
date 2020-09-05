package ru.team42.analyzer.dto.converters;

import ru.team42.analyzer.dto.response.MessengerDto;
import ru.team42.analyzer.entities.MessengerEntity;

import javax.validation.constraints.NotNull;

public class MessengerEntityToMessengerDtoConverter implements org.springframework.core.convert.converter.Converter<MessengerEntity, MessengerDto> {
    @Override
    public MessengerDto convert(@NotNull MessengerEntity entity) {
        return new MessengerDto(entity.getId(), entity.getName(), entity.getUserId()
        );
    }
}

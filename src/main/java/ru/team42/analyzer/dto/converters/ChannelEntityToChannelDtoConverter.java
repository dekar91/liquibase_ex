package ru.team42.analyzer.dto.converters;

import ru.team42.analyzer.dto.response.ChannelDto;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.MessengerEntity;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

public class ChannelEntityToChannelDtoConverter implements org.springframework.core.convert.converter.Converter<ChannelEntity, ChannelDto> {
    @Override
    public ChannelDto convert(@NotNull ChannelEntity entity) {
        return new ChannelDto(
                entity.getId(),
                entity.getName(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                entity.getMessengers()
                        .stream()
                        .mapToLong(MessengerEntity::getId)
                        .boxed()
                        .collect(Collectors.toList()),
                entity.getButtons()
                        .stream()
                        .mapToLong(ButtonEntity::getId)
                        .boxed()
                        .collect(Collectors.toList())
        );
    }
}

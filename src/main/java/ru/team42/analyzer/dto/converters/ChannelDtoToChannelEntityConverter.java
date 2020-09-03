package ru.team42.analyzer.dto.converters;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.team42.analyzer.dto.response.ChannelDto;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.MessengerEntity;

import java.util.stream.Collectors;

public class ChannelDtoToChannelEntityConverter implements Converter<ChannelDto, ChannelEntity> {
    @Override
    public ChannelEntity convert(@NotNull ChannelDto source) {
        ChannelEntity channelEntity = new ChannelEntity();
        BeanUtils.copyProperties(source, channelEntity);

        channelEntity.setButtons(
                source.getButtonsId().stream().map(id -> {
                    ButtonEntity button = new ButtonEntity();
                    button.setId(id);

                    return button;
                })
                        .collect(Collectors.toList())
        );

        channelEntity.setMessengers(
                source.getMessengersIds().stream().map(id -> {
                    MessengerEntity button = new MessengerEntity();
                    button.setId(id);

                    return button;
                })
                        .collect(Collectors.toList())
        );

        return channelEntity;
    }
}

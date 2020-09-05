package ru.team42.analyzer.dto.converters;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.team42.analyzer.dto.response.MessengerDto;
import ru.team42.analyzer.entities.MessengerEntity;

public class MessengerDtoToMessengerEntityConverter implements Converter<MessengerDto, MessengerEntity> {
    @Override
    public MessengerEntity convert(@NotNull MessengerDto source) {
        MessengerEntity messengerEntity = new MessengerEntity();
        BeanUtils.copyProperties(source, messengerEntity);

        return messengerEntity;
    }
}

package ru.team42.analyzer.dto.converters;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.team42.analyzer.dto.response.ButtonDto;
import ru.team42.analyzer.entities.ButtonEntity;

public class ButtonDtoToButtonEntityConverter implements Converter<ButtonDto, ButtonEntity> {
    @Override
    public ButtonEntity convert(@NotNull ButtonDto source) {
        ButtonEntity buttonEntity = new ButtonEntity();
        BeanUtils.copyProperties(source, buttonEntity);

        return buttonEntity;
    }
}

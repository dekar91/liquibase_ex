package ru.team42.analyzer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.dto.response.ButtonDto;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.repositories.ButtonRepository;
import ru.team42.analyzer.services.interfaces.ButtonService;

@Service
@Transactional
public class ButtonServiceImpl extends BasicService<ButtonEntity, ButtonDto> implements ButtonService {

    @Autowired
    ButtonServiceImpl(ButtonRepository repository, ConversionService conversionService) {
        super(repository, conversionService, ButtonEntity.class, ButtonDto.class);
    }
}

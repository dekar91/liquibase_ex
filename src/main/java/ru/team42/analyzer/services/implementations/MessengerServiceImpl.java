package ru.team42.analyzer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.dto.response.MessengerDto;
import ru.team42.analyzer.entities.MessengerEntity;
import ru.team42.analyzer.repositories.MessengerRepository;
import ru.team42.analyzer.services.interfaces.MessengerService;


@Service
@Transactional
public class MessengerServiceImpl extends BasicService<MessengerEntity, MessengerDto> implements MessengerService {

    @Autowired
    MessengerServiceImpl(MessengerRepository repository, ConversionService conversionService) {
        super(repository, conversionService, MessengerEntity.class, MessengerDto.class);
    }
}

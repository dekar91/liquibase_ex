package ru.team42.analyzer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.dto.response.ChannelDto;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.repositories.ChannelRepository;
import ru.team42.analyzer.services.interfaces.ChannelService;

@Service
@Transactional
public class ChannelServiceImpl extends BasicService<ChannelEntity, ChannelDto> implements ChannelService {

    @Autowired
    ChannelServiceImpl(ChannelRepository repository, ConversionService conversionService) {
        super(repository, conversionService, ChannelEntity.class, ChannelDto.class);
    }
}
package ru.team42.analyzer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.dto.response.ChannelDto;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.repositories.ChannelRepository;
import ru.team42.analyzer.services.interfaces.ChannelService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ConversionService conversionService;

    @Autowired
    ChannelServiceImpl(ChannelRepository channelRepository, ConversionService conversionService) {
        this.channelRepository = channelRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<ChannelDto> getAll(Long id) {
        return channelRepository.findAll().stream()
                .map(entity -> conversionService.convert(entity, ChannelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ChannelDto getById(Long id) {
        return conversionService.convert(channelRepository.findById(id).orElse(null), ChannelDto.class);
    }

    @Override
    public ChannelDto save(ChannelDto dto) {
        ChannelEntity channelEntity = conversionService.convert(dto, ChannelEntity.class);

        if(channelEntity != null) {
            channelEntity = channelRepository.save(channelEntity);

            return conversionService.convert(channelEntity, ChannelDto.class);
        }

        return dto;
    }

    @Override
    public void delete(Long id) {
        channelRepository.deleteById(id);
    }

}

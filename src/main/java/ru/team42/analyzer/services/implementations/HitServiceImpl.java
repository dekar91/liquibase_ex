package ru.team42.analyzer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.dto.response.HitDto;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.repositories.HitRepository;
import ru.team42.analyzer.services.interfaces.HitService;

@Service
@Transactional
public class HitServiceImpl extends BasicService<HitEntity, HitDto> implements HitService {

    @Autowired
    HitServiceImpl(HitRepository repository, ConversionService conversionService) {
        super(repository, conversionService, HitEntity.class, HitDto.class);
    }
}
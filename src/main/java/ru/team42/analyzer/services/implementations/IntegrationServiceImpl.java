package ru.team42.analyzer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.dto.response.IntegrationDto;
import ru.team42.analyzer.entities.IntegrationEntity;
import ru.team42.analyzer.repositories.IntegrationRepository;
import ru.team42.analyzer.services.interfaces.IntegrationService;

@Service
@Transactional
public class IntegrationServiceImpl extends BasicService<IntegrationEntity, IntegrationDto> implements IntegrationService {

    @Autowired
    IntegrationServiceImpl(IntegrationRepository repository, ConversionService conversionService) {
        super(repository, conversionService, IntegrationEntity.class, IntegrationDto.class);
    }
}
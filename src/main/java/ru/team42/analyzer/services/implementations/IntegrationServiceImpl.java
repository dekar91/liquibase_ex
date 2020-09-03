package ru.team42.analyzer.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.team42.analyzer.dto.response.IntegrationDto;
import ru.team42.analyzer.entities.IntegrationEntity;
import ru.team42.analyzer.repositories.IntegrationRepository;
import ru.team42.analyzer.services.interfaces.IntegrationService;

import java.util.List;
import java.util.stream.Collectors;

public class IntegrationServiceImpl implements IntegrationService {

    private final IntegrationRepository integrationRepository;

    @Autowired
    IntegrationServiceImpl(IntegrationRepository integrationRepository) {
        this.integrationRepository = integrationRepository;
    }

    @Override
    public List<IntegrationDto> getAll(Long id) {
        return integrationRepository.findAll().parallelStream()
                .map(IntegrationServiceImpl::wrapEntity)
                .collect(Collectors.toList());
    }

    @Override
    public IntegrationDto getById(Long id) {
        return wrapEntity(integrationRepository.findById(id).orElse(null));
    }

    @Override
    public IntegrationDto save(IntegrationDto dto) {
        IntegrationEntity integrationEntity = new IntegrationEntity();
        BeanUtils.copyProperties(dto, integrationEntity);

        integrationRepository.save(integrationEntity);

        return null;
    }

    @Override
    public void delete(Long id) {
        integrationRepository.deleteById(id);

    }

    private static IntegrationDto wrapEntity(IntegrationEntity entity) {
        if (entity == null) {
            return null;
        }

        return new IntegrationDto(entity.getId(), entity.getName());
    }
    
}

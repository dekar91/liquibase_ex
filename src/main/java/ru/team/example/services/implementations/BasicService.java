package ru.team.example.services.implementations;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public abstract class BasicService<ENTITY, DTO> {


    protected final ConversionService conversionService;
    protected final JpaRepository<ENTITY, Long> repository;

    private final Class<ENTITY> entityClass;
    private final Class<DTO> dtoClass;

    protected BasicService(JpaRepository<ENTITY, Long> repository, ConversionService conversionService, Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public List<DTO> getAll() {
        return repository.findAll().stream()
                .map(entity -> conversionService.convert(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public DTO getById(Long id) {
        return conversionService.convert(repository.findById(id).orElse(null), dtoClass);
    }

    public DTO save(DTO dto) {
        return  conversionService.convert(
                repository.save(conversionService.convert(dto, entityClass)),
                dtoClass);
    }

    public void delete(Long id) {
        repository.deleteById(id);

    }
}

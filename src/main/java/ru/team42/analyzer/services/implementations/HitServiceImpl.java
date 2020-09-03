package ru.team42.analyzer.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team42.analyzer.dto.response.HitDto;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.repositories.HitRepository;
import ru.team42.analyzer.services.interfaces.HitService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Autowired
    HitServiceImpl(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    @Override
    public List<HitDto> getAll(Long id) {
        return hitRepository.findAll().parallelStream()
                .map(HitServiceImpl::wrapEntity)
                .collect(Collectors.toList());
    }

    @Override
    public HitDto getById(Long id) {
        return wrapEntity(hitRepository.findById(id).orElse(null));
    }

    @Override
    public HitDto save(HitDto dto) {
        HitEntity hitEntity = new HitEntity();
        BeanUtils.copyProperties(dto, hitEntity);

        ButtonEntity button = new ButtonEntity();
        button.setId(dto.getButtonId());

        hitEntity.setButton(button);

        HitEntity entity = hitRepository.save(hitEntity);

        return new HitDto(entity.getId(), entity.getButton().getId(), entity.getUrl(), entity.getAction(), entity.getData());
    }

    @Override
    public void delete(Long id) {
        hitRepository.deleteById(id);

    }

    private static HitDto wrapEntity(HitEntity entity) {
        if (entity == null) {
            return null;
        }

        return new HitDto(entity.getId(),
                entity.getButton().getId(),
                entity.getUrl(),
                entity.getAction(), entity.getData());
    }
}

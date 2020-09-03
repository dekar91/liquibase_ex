package ru.team42.analyzer.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team42.analyzer.dto.response.MessengerDto;
import ru.team42.analyzer.entities.MessengerEntity;
import ru.team42.analyzer.repositories.MessengerRepository;
import ru.team42.analyzer.services.interfaces.MessengerService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MessengerServiceImpl implements MessengerService {

    private final MessengerRepository messengerRepository;

    @Autowired
    MessengerServiceImpl(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    @Override
    public List<MessengerDto> getAll(Long id) {
        return messengerRepository.findAll().parallelStream()
                .map(MessengerServiceImpl::wrapEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MessengerDto getById(Long id) {
        return wrapEntity(messengerRepository.findById(id).orElse(null));
    }

    @Override
    public MessengerDto save(MessengerDto dto) {
        MessengerEntity messengerEntity = new MessengerEntity();
        BeanUtils.copyProperties(dto, messengerEntity);

        messengerRepository.save(messengerEntity);

        return null;
    }

    @Override
    public void delete(Long id) {
        messengerRepository.deleteById(id);

    }

    private static MessengerDto wrapEntity(MessengerEntity entity) {
        if(entity == null) {
            return null;
        }

        return new MessengerDto(entity.getId(), entity.getName(), entity.getUserId());
    }
}

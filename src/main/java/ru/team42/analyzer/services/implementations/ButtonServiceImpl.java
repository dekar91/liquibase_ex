package ru.team42.analyzer.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team42.analyzer.dto.response.ButtonDto;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.repositories.ButtonRepository;
import ru.team42.analyzer.services.interfaces.ButtonService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ButtonServiceImpl implements ButtonService {
    private final ButtonRepository buttonRepository;

    @Autowired
    ButtonServiceImpl(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    @Override
    public List<ButtonDto> getAll(Long id) {
        return buttonRepository.findAll().parallelStream()
                .map(ButtonServiceImpl::wrapEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ButtonDto getById(Long id) {
        return wrapEntity(buttonRepository.findById(id).orElse(null));
    }

    @Override
    public ButtonDto save(ButtonDto dto) {
        ButtonEntity buttonEntity = new ButtonEntity();
        BeanUtils.copyProperties(dto, buttonEntity);

        buttonRepository.save(buttonEntity);

        return null;
    }

    @Override
    public void delete(Long id) {
        buttonRepository.deleteById(id);

    }

    private static ButtonDto wrapEntity(ButtonEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ButtonDto(entity.getId(), entity.getName(),
                entity.getChannel().getId(), entity.getMessenger().getId()
                );
    }
}

package ru.team42.analyzer.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team42.analyzer.dto.HitRequest;
import ru.team42.analyzer.dto.MessengerType;
import ru.team42.analyzer.dto.response.ButtonApiSettingsResponse;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.repositories.ButtonRepository;
import ru.team42.analyzer.repositories.HitRepository;
import ru.team42.analyzer.services.interfaces.ApiService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements ApiService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final ButtonRepository buttonRepository;
    private final HitRepository hitRepository;

    @Autowired
    AppServiceImpl(ButtonRepository buttonRepository, HitRepository hitRepository) {
        this.buttonRepository = buttonRepository;
        this.hitRepository = hitRepository;
    }

    public List<ButtonApiSettingsResponse> getConfig(List<String> channels) {
        List<ButtonApiSettingsResponse> buttonSettings = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();

        List<Long> c = channels.stream().mapToLong(x -> Long.parseLong(x.split("_")[1])).boxed().collect(Collectors.toList());

        buttonRepository.findAllById(c).forEach(cl -> {
            buttonSettings.add(new ButtonApiSettingsResponse( channels.get(i.getAndIncrement()), MessengerType.TELEGRAM, "", Collections.emptyList()));
        });

        return buttonSettings;
    }


    private static Long extractButtonId(String entityString) {
        String[] parts = entityString.split("_");

        return parts.length > 0 ? Long.parseLong(parts[1]) : null;
    }

    public void hit(HitRequest data) {
        UserEntity user = new UserEntity();

        ButtonEntity buttonEntity = buttonRepository.findById(Objects.requireNonNull(extractButtonId(data.getButtonId()))).orElse(null);

        HitEntity hitEntity = new HitEntity();

        BeanUtils.copyProperties(data, hitEntity);

        hitRepository.save(hitEntity);
    }
}

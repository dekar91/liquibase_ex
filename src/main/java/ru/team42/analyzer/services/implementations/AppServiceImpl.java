package ru.team42.analyzer.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team42.analyzer.dto.ChannelRenderSetting;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.entities.Role;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.repositories.ChannelRepository;
import ru.team42.analyzer.repositories.HitRepository;
import ru.team42.analyzer.services.interfaces.AppService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final ChannelRepository channelRepository;
    private final HitRepository hitRepository;

    AppServiceImpl(@Autowired ChannelRepository channelRepository, @Autowired HitRepository hitRepository) {
        this.channelRepository = channelRepository;
        this.hitRepository = hitRepository;
    }

    public List<ChannelRenderSetting> configByJsClasses(List<String> channels) {

        List<Long> c = channels.stream().mapToLong(x -> Long.parseLong(x.split("_")[1])).boxed().collect(Collectors.toList());

        List<ChannelRenderSetting> channelSettings = new ArrayList<>();

        AtomicInteger i = new AtomicInteger();

        channelRepository.findAllById(c).forEach(cl -> {
            channelSettings.add(new ChannelRenderSetting( channels.get(i.getAndIncrement()).toString(), "https://vk.com/" + cl.getId()));
        });

        return channelSettings;
    }

    public List<ChannelRenderSetting> config(List<Long> channels) {

        List<ChannelRenderSetting> channelSettings = new ArrayList<>();

        channelRepository.findAllById(channels).forEach(c -> {
            channelSettings.add(new ChannelRenderSetting(c.getId().toString() + '_', "https://vk.com/" + c.getId()));
        });

        return channelSettings;
    }

    private Long extractChannelId(String entityString) {
        String[] parts = entityString.split("_");

        return parts.length > 0 ? Long.parseLong(parts[1]) : null;
    }

    public String hit(String channelId, String data) {
        UserEntity user = new UserEntity();

        ChannelEntity channelEntity = channelRepository.findById(Objects.requireNonNull(extractChannelId(channelId))).orElse(null);

        HitEntity entity = new HitEntity();
        entity.setChannel(channelEntity);
        entity.setData(data);

        Role role = new Role();
        role.setName("test");

        entityManager.persist(role);

        user.setRoles(Collections.singleton(role));
        entityManager.persist(user);

        entityManager.persist(channelEntity);
        hitRepository.save(entity);

        return "OK";
    }
}

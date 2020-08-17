package ru.team42.analyzer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.repositories.HitRepository;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api")
public class ApiController extends BasicController {

    @Autowired
    private HitRepository hitRepository;

    @PostMapping("{channelId}/{messengerId}")
    @ResponseBody
    @Transactional
    public String hit(@RequestParam Long channelId, @RequestParam Long messengerId, @RequestBody String data) {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId(channelId);
        channelEntity.setUser(user);

        HitEntity entity = new HitEntity();
        entity.setChannel(channelEntity);
        entity.setData(data);

        entityManager.persist(user);
        entityManager.persist(channelEntity);
        hitRepository.save(entity);

        return "OK";
    }

}

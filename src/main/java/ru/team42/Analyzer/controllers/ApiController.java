package ru.team42.analyzer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.entities.Role;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.repositories.HitRepository;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import java.util.Collections;

@RestController
@RequestMapping("api")
@PermitAll
public class ApiController extends BasicController {

    @Autowired
    private HitRepository hitRepository;

    @GetMapping
    public String test(){
        return "test";
    }

    @PostMapping("{channelId}/{messengerId}")
    @ResponseBody
    @Transactional
    public String hit(@PathVariable Long channelId, @PathVariable Long messengerId, @RequestBody String data) {
        UserEntity user = new UserEntity();

        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setUser(user);

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

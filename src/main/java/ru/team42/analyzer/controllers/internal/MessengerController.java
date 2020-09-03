package ru.team42.analyzer.controllers.internal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.entities.MessengerEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.MessengerRepository;

import java.util.List;

/**
 * Internal controller for messenger management
 */
@RestController
@RequestMapping("messenger")
public class MessengerController extends BasicController {

    private final MessengerRepository messengerRepository;

    @Autowired
    MessengerController(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<MessengerEntity>> getList() {
        return ApiResponse.buildWithData(Lists.newArrayList(messengerRepository.findAll()));
    };


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<MessengerEntity> getMessenger(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(messengerRepository.findById(id).orElse(null));
    };

    @PostMapping()
    @ResponseBody
    public ApiResponse<MessengerEntity> createMessenger(@RequestBody MessengerEntity messenger) {
        messengerRepository.save(messenger);

        return ApiResponse.buildWithData(messenger);
    }


    @PutMapping()
    @ResponseBody
    public  ApiResponse<MessengerEntity> updateMessenger(@RequestBody MessengerEntity messenger) {
        messengerRepository.save(messenger);

        return ApiResponse.buildWithData(messenger);
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteMessenger(@PathVariable("id") Long id) {
        messengerRepository.deleteById(id);

        return ApiResponse.buildWithData(true);
    }

}

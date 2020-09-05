package ru.team42.analyzer.controllers.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.dto.response.MessengerDto;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.services.interfaces.MessengerService;

import java.util.List;

/**
 * Internal controller for messenger management
 */
@RestController
@RequestMapping("messenger")
public class MessengerController extends BasicController {

    private final MessengerService messengerService;

    @Autowired
    MessengerController(MessengerService messengerService) {
        this.messengerService = messengerService;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<MessengerDto>> getList() {
        return ApiResponse.buildWithData(
                messengerService.getAll()
        );
    }


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<MessengerDto> getMessenger(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(messengerService.getById(id));
    }

    @PostMapping()
    @ResponseBody
    public ApiResponse<MessengerDto> createMessenger(@RequestBody MessengerDto messenger) {
        return ApiResponse.buildWithData(messengerService.save(messenger));
    }


    @PutMapping()
    @ResponseBody
    public  ApiResponse<MessengerDto> updateMessenger(@RequestBody MessengerDto messenger) {
        return ApiResponse.buildWithData(messengerService.save(messenger));
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteMessenger(@PathVariable("id") Long id) {
        messengerService.delete(id);

        return ApiResponse.buildWithData(true);
    }

}

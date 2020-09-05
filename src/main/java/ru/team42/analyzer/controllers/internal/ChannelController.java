package ru.team42.analyzer.controllers.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import ru.team42.analyzer.dto.response.ChannelDto;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.services.interfaces.ChannelService;

import java.util.List;

/**
 * Internal controller for channel management
 */
@RestController
@RequestMapping("channel")
public class ChannelController extends BasicController {

    private final ChannelService channelService;

    @Autowired
    ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    /**
     * Bla bla
     */
    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<ChannelDto>> getList() {
        return ApiResponse.buildWithData(channelService.getAll());
    }


    @GetMapping("{id}")
    @ResponseBody
    @Transactional
    public ApiResponse<ChannelDto> getChannel(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(channelService.getById(id));
    }

    @PostMapping()
    @ResponseBody
    public ApiResponse<ChannelDto> createChannel(@RequestBody ChannelDto channel) {

        return ApiResponse.buildWithData(channelService.save(channel));
    }


    @PutMapping
    @ResponseBody
    public  ApiResponse<ChannelDto> updateChannel(@RequestBody ChannelDto channel) {

        return ApiResponse.buildWithData(channelService.save(channel));
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteChannel(@PathVariable("id") Long id) {
        channelService.delete(id);

        return ApiResponse.buildWithData(true);
    }

}

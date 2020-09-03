package ru.team42.analyzer.controllers.internal;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.dto.response.ChannelDto;
import ru.team42.analyzer.entities.ChannelEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.ChannelRepository;
import ru.team42.analyzer.services.interfaces.ChannelService;

import java.util.List;

/**
 * Internal controller for channel management
 */
@RestController
@RequestMapping("channel")
public class ChannelController extends BasicController {

    private final ChannelRepository channelRepository;
    private final ChannelService channelService;

    @Autowired
    ChannelController(ChannelRepository channelRepository, ChannelService channelService) {
        this.channelRepository = channelRepository;
        this.channelService = channelService;
    }

    /**
     * Bla bla
     */
    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<ChannelEntity>> getList() {
        return ApiResponse.buildWithData(Lists.newArrayList(channelRepository.findAll()));
    }


    @GetMapping("{id}")
    @ResponseBody
    @Transactional
    public ApiResponse<ChannelDto> getChannel(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(channelService.getById(id));
    }

    @PostMapping()
    @ResponseBody
    public ApiResponse<ChannelEntity> createChannel(@RequestBody ChannelEntity channel) {
        channelRepository.save(channel);

        return ApiResponse.buildWithData(channel);
    }


    @PutMapping
    @ResponseBody
    public  ApiResponse<ChannelEntity> updateChannel(@RequestBody ChannelEntity channel) {
        channelRepository.save(channel);

        return ApiResponse.buildWithData(channel);
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteChannel(@PathVariable("id") Long id) {
        channelRepository.deleteById(id);

        return ApiResponse.buildWithData(true);
    }

}

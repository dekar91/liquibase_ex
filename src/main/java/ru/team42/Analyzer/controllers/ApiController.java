package ru.team42.analyzer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.dto.ChannelRenderSetting;
import ru.team42.analyzer.services.interfaces.AppService;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Controller for hitting script
 */
@RestController
@RequestMapping("api")
@PermitAll
@CrossOrigin(origins = "*")
public class ApiController extends BasicController {


    private final AppService appService;

    public ApiController(@Autowired AppService appService) {
        this.appService = appService;
    }

    /**
     * Methoo returns configuration for the selected channels
     * @param channels - Channels list
     * @return Array of configurations
     */
    @GetMapping("config")
    @ResponseBody
    public List<ChannelRenderSetting> config(@RequestParam List<String> channels) {

        return appService.configByJsClasses(channels);
    }

    @PostMapping("{channelId}")
    @ResponseBody
    @Transactional
    public String hit(@PathVariable String channelId, @RequestBody String data) {

        appService.hit(channelId, data);
        return "ok";
    }

}

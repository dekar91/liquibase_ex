package ru.team42.analyzer.controllers.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.dto.HitRequest;
import ru.team42.analyzer.dto.response.ButtonApiSettingsResponse;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.services.interfaces.ApiService;

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


    private final ApiService appService;

    public ApiController(@Autowired ApiService appService) {
        this.appService = appService;
    }

    /**
     * Method returns configuration for rendering buttons on client page
     * @param buttonClasses String ButtonClasses list
     * @return Array of configurations
     */
    @GetMapping("config")
    @ResponseBody
    public ApiResponse<List<ButtonApiSettingsResponse>> config(@RequestParam List<String> buttonClasses) {

        return ApiResponse.buildWithData(appService.getConfig(buttonClasses));
    }

    /**
     * Method to count page visits
     * @param hitRequest HitRequest entity with information about the hit
     */
    @PostMapping("hit")
    @ResponseBody
    @Transactional
    public void hit(@RequestBody HitRequest hitRequest) {
        appService.hit(hitRequest);
    }

}

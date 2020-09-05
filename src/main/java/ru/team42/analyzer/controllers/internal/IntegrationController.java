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
import ru.team42.analyzer.dto.response.IntegrationDto;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.services.interfaces.IntegrationService;

import java.util.List;

/**
 * Internal controller for integration management
 */
@RestController
@RequestMapping("integration")
public class IntegrationController extends BasicController {

    private final IntegrationService integrationService;

    @Autowired
    IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<IntegrationDto>> getList() {
        return ApiResponse.buildWithData(integrationService.getAll());
    };


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<IntegrationDto> getIntegration(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(integrationService.getById(id));
    };

    @PostMapping()
    @ResponseBody
    public ApiResponse<IntegrationDto> createIntegration(@RequestBody IntegrationDto integration) {

        return ApiResponse.buildWithData(integrationService.save(integration));
    }


    @PutMapping()
    @ResponseBody
    public  ApiResponse<IntegrationDto> updateIntegration(@RequestBody IntegrationDto integration) {
        return ApiResponse.buildWithData(integrationService.save(integration));

    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteIntegration(@PathVariable("id") Long id) {
        integrationService.delete(id);

        return ApiResponse.buildWithData(true);
    }

}

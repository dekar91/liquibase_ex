package ru.team42.analyzer.controllers.internal;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.entities.IntegrationEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.IntegrationRepository;

import java.util.List;

/**
 * Internal controller for integration management
 */
@RestController
@RequestMapping("integration")
public class IntegrationController extends BasicController {

    private final IntegrationRepository integrationRepository;

    @Autowired
    IntegrationController(IntegrationRepository integrationRepository) {
        this.integrationRepository = integrationRepository;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<IntegrationEntity>> getList() {
        return ApiResponse.buildWithData(Lists.newArrayList(integrationRepository.findAll()));
    };


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<IntegrationEntity> getIntegration(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(integrationRepository.findById(id).orElse(null));
    };

    @PostMapping()
    @ResponseBody
    public ApiResponse<IntegrationEntity> createIntegration(@RequestBody IntegrationEntity integration) {
        integrationRepository.save(integration);

        return ApiResponse.buildWithData(integration);
    }


    @PutMapping()
    @ResponseBody
    public  ApiResponse<IntegrationEntity> updateIntegration(@RequestBody IntegrationEntity integration) {
        integrationRepository.save(integration);

        return ApiResponse.buildWithData(integration);
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteIntegration(@PathVariable("id") Long id) {
        integrationRepository.deleteById(id);

        return ApiResponse.buildWithData(true);
    }

}

package ru.team42.analyzer.controllers.internal;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.dto.response.HitDto;
import ru.team42.analyzer.entities.HitEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.HitRepository;
import ru.team42.analyzer.services.interfaces.HitService;

import java.util.List;

/**
 * Internal controller for hit management
 */
@RestController
@RequestMapping("hit")
public class HitController extends BasicController {

    private final HitRepository hitRepository;
    private final HitService hitService;

    @Autowired
    HitController(HitRepository hitRepository, HitService hitService) {
        this.hitRepository = hitRepository;
        this.hitService = hitService;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<HitEntity>> getList() {
        return ApiResponse.buildWithData(Lists.newArrayList(hitRepository.findAll()));
    };


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<HitEntity> getHit(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(hitRepository.findById(id).orElse(null));
    };

    @PostMapping()
    @ResponseBody
    public ApiResponse<HitDto> createHit(@RequestBody HitDto hit) {
        return ApiResponse.buildWithData(hitService.save(hit));
    }


    @PutMapping()
    @ResponseBody
    public  ApiResponse<HitEntity> updateHit(@RequestBody HitEntity hit) {
        hitRepository.save(hit);

        return ApiResponse.buildWithData(hit);
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteHit(@PathVariable("id") Long id) {
        hitRepository.deleteById(id);

        return ApiResponse.buildWithData(true);
    }

}

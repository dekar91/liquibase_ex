package ru.team42.analyzer.controllers.internal;

import com.google.common.collect.Lists;
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
import ru.team42.analyzer.dto.response.HitDto;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.services.interfaces.HitService;

import java.util.List;

/**
 * Internal controller for hit management
 */
@RestController
@RequestMapping("hit")
public class HitController extends BasicController {

    private final HitService hitService;

    @Autowired
    HitController( HitService hitService) {
        this.hitService = hitService;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<HitDto>> getList() {
        return ApiResponse.buildWithData(Lists.newArrayList(hitService.getAll()));
    };


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<HitDto> getHit(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(hitService.getById(id));
    };

    @PostMapping()
    @ResponseBody
    public ApiResponse<HitDto> createHit(@RequestBody HitDto hit) {
        return ApiResponse.buildWithData(hitService.save(hit));
    }


    @PutMapping()
    @ResponseBody
    public  ApiResponse<HitDto> updateHit(@RequestBody HitDto hit) {

        return ApiResponse.buildWithData(hitService.save(hit));
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteHit(@PathVariable("id") Long id) {
        hitService.delete(id);

        return ApiResponse.buildWithData(true);
    }

}

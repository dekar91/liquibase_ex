package ru.team42.analyzer.controllers.internal;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.entities.ButtonEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.ButtonRepository;

import java.util.List;

/**
 * Internal controller for button management
 */
@RestController
@RequestMapping("button")
public class ButtonController extends BasicController {

    private final ButtonRepository buttonRepository;

    @Autowired
    ButtonController(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<ButtonEntity>> getList() {
        return ApiResponse.buildWithData(Lists.newArrayList(buttonRepository.findAll()));
    };


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<ButtonEntity> getButton(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(buttonRepository.findById(id).orElse(null));
    };

    @PostMapping()
    @ResponseBody
    public ApiResponse<ButtonEntity> createButton(@RequestBody ButtonEntity button) {
        buttonRepository.save(button);

        return ApiResponse.buildWithData(button);
    }


    @PutMapping()
    @ResponseBody
    public  ApiResponse<ButtonEntity> updateButton(@RequestBody ButtonEntity button) {
        buttonRepository.save(button);

        return ApiResponse.buildWithData(button);
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteButton(@PathVariable("id") Long id) {
        buttonRepository.deleteById(id);

        return ApiResponse.buildWithData(true);
    }

}

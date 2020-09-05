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
import ru.team42.analyzer.dto.response.ButtonDto;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.services.interfaces.ButtonService;

import java.util.List;

/**
 * Internal controller for button management
 */
@RestController
@RequestMapping("button")
public class ButtonController extends BasicController {

    private final ButtonService buttonService;

    @Autowired
    ButtonController(ButtonService buttonService) {
        this.buttonService = buttonService;
    }

    @GetMapping("list")
    @ResponseBody
    public ApiResponse<List<ButtonDto>> getList() {
        return ApiResponse.buildWithData(buttonService.getAll());
    };


    @GetMapping("{id}")
    @ResponseBody
    public ApiResponse<ButtonDto> getButton(@PathVariable("id") Long id) {
        return ApiResponse.buildWithData(buttonService.getById(id));
    };

    @PostMapping()
    @ResponseBody
    public ApiResponse<ButtonDto> createButton(@RequestBody ButtonDto button) {

        return ApiResponse.buildWithData(buttonService.save(button));
    }

    @PutMapping()
    @ResponseBody
    public  ApiResponse<ButtonDto> updateButton(@RequestBody ButtonDto button) {

        return ApiResponse.buildWithData(buttonService.save(button));
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteButton(@PathVariable("id") Long id) {
        buttonService.delete(id);

        return ApiResponse.buildWithData(true);
    }

}

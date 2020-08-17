package ru.team42.analyzer.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("hit")
public class HitController extends BasicController {

    @GetMapping
    @ResponseBody
    public String hit() {
        return "ff";
    }

    @GetMapping("config")
    @ResponseBody
    public String getConfig(@RequestParam String[] channels) {
        return Arrays.toString(channels);
    }

}

package ru.team42.analyzer.controllers;

import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.entities.ChannelEntity;

@RestController
@RequestMapping("channel")
public abstract class ChannelController extends BasicController {

    @GetMapping("/channel/{id}")
    @ResponseBody
    public abstract ChannelEntity getChannel(@PathVariable("id") Long id);

    @PostMapping("/channel/create")
    @ResponseBody
    public abstract ChannelEntity createChannel(@RequestBody ChannelEntity channel);


    @GetMapping("/channel/{id}/update")
    @ResponseBody
    public abstract ChannelEntity updateChannel(@PathVariable("id") Long id, @RequestBody ChannelEntity channel);

    @GetMapping("/channel/{id}/delete")
    public abstract void deleteChannel(@PathVariable("id") Long id);

}

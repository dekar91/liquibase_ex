package ru.team42.Analyzer.controllers;

import org.springframework.web.bind.annotation.*;
import ru.team42.Analyzer.entities.AuthResponse;

@RestController
public class AuthController extends BasicController {

    @PostMapping("/login")
    @ResponseBody
     public AuthResponse login(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password) {
        return new AuthResponse();
    }

    @GetMapping("/logout")
    public void logout(){

    }

    @PutMapping("/signup")
    public void signUp() {

    }

}

package ru.team42.analyzer.controllers;

import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.entities.AuthResponse;

@RestController
public class AuthController extends BasicController {

    /**  Logouts user
     * Logouts user
     * @param login - user login
     * @param password - user password
     * @return Ttt
     */
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
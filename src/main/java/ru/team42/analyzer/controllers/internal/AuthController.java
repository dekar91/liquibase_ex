package ru.team42.analyzer.controllers.internal;

import org.springframework.web.bind.annotation.*;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.dto.response.AuthResponse;
import ru.team42.analyzer.jsonApi.ApiResponse;

/**
 * Internal controller for user authorization
 */
@RestController
public class AuthController extends BasicController {

    /**  Login user.
     * Login should match chat2desk login and password with C2D password.
     * Logouts user
     * @param login - user login
     * @param password - user password
     * @return AuthResponse with information a about token.
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiResponse<AuthResponse> login(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password) {
        return ApiResponse.buildWithData(new AuthResponse("token"));
    }

    /**
     * Invalidates user session
     */
    @GetMapping("/logout")
    public void logout() {

    }

}

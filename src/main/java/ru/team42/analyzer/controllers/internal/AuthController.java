package ru.team42.analyzer.controllers.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.team42.analyzer.config.jwt.JwtTokenProvider;
import ru.team42.analyzer.controllers.BasicController;
import ru.team42.analyzer.dto.request.AuthRequest;
import ru.team42.analyzer.dto.response.AuthResponse;
import ru.team42.analyzer.entities.RoleEntity;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.repositories.RoleRepository;
import ru.team42.analyzer.repositories.UserRepository;
import ru.team42.analyzer.services.interfaces.UserService;

import java.util.stream.Collectors;

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
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ResponseBody
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest data) {

        try {
            String username = data.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));

            return ApiResponse.buildWithData(new AuthResponse(username, token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
//    @PostMapping("/login")
//    @ResponseBody
//    public ApiResponse<AuthResponse> login(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password) {
//        return ApiResponse.buildWithData(new AuthResponse("token"));
//    }

    /**
     * Invalidates user session
     */
    @GetMapping("/logout")
    public void logout() {

    }

}

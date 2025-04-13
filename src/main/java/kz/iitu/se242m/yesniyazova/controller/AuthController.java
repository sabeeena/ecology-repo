package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.dto.AuthResponse;
import kz.iitu.se242m.yesniyazova.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User body) {
        return auth.registerUser(body);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-admin")
    public User registerAdmin(@RequestBody User body) {
        return auth.registerAdmin(body);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestParam String username,
                              @RequestParam String password) {
        return auth.login(username, password);
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String username) {
        auth.logout(username);
        return "Logged out";
    }
}
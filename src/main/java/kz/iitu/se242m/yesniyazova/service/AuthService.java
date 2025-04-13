package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.dto.AuthResponse;

public interface AuthService {
    User registerUser(User userRequest);
    User registerAdmin(User adminRequest);
    AuthResponse login(String username, String rawPassword);
    void logout(String username);
}

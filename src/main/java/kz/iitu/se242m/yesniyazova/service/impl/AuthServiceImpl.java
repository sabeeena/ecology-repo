package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.UserRole;
import kz.iitu.se242m.yesniyazova.entity.dto.AuthResponse;
import kz.iitu.se242m.yesniyazova.repository.UserRepository;
import kz.iitu.se242m.yesniyazova.repository.UserRoleRepository;
import kz.iitu.se242m.yesniyazova.security.JwtUtil;
import kz.iitu.se242m.yesniyazova.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final UserRoleRepository roleRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthServiceImpl(UserRepository userRepo,
                           UserRoleRepository roleRepo,
                           BCryptPasswordEncoder encoder,
                           JwtUtil jwt) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @Override
    public User registerUser(User req) {
        return persistNewUser(req, "USER");
    }

    @Override
    public User registerAdmin(User req) {
        return persistNewUser(req, "ADMIN");
    }

    private User persistNewUser(User req, String roleCode) {
        if (userRepo.existsByUsername(req.getUsername()))
            throw new RuntimeException("Username already taken");
        if (userRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already in use");

        UserRole role = roleRepo.findByCode(roleCode)
                .orElseThrow(() -> new RuntimeException("Role '" + roleCode + "' not found"));

        req.setRole(role);
        req.setPassword(encoder.encode(req.getPassword()));
        req.setActive(true);
        req.setCreatedAt(LocalDateTime.now());
        req.setModifiedAt(LocalDateTime.now());

        return userRepo.save(req);
    }

    @Override
    public AuthResponse login(String username, String rawPassword) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(rawPassword, user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String token = jwt.generateToken(user.getUsername(), user.getRole().getCode());
        return new AuthResponse(token, user.getUsername(), user.getRole().getCode());
    }

    @Override
    public void logout(String username) {
        System.out.println("User '" + username + "' logged out (stateless).");
    }
}
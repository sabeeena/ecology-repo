package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.repository.UserRepository;
import kz.iitu.se242m.yesniyazova.service.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User me(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User update(String username, User patch) {
        User u = userRepository.findByUsername(username).orElseThrow();
        u.setFirstName(patch.getFirstName());
        u.setLastName(patch.getLastName());
        u.setPhoneNumber(patch.getPhoneNumber());
        u.setEmail(patch.getEmail());
        return userRepository.save(u);
    }

    public void deactivate(String username) {
        User u = userRepository.findByUsername(username).orElseThrow();
        u.setActive(false);
        userRepository.save(u);
    }
}

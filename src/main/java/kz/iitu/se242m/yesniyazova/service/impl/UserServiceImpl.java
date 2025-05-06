package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.dto.UserDto;
import kz.iitu.se242m.yesniyazova.entity.dto.UserFilterDto;
import kz.iitu.se242m.yesniyazova.repository.UserRepository;
import kz.iitu.se242m.yesniyazova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Value("${pagination.default-size}")
    private int defaultPageSize;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<UserDto> getUsersByFilter(UserFilterDto filter, Pageable pageable) {
        Pageable resolvedPageable = pageable.isPaged()
                ? pageable
                : PageRequest.of(0, defaultPageSize);

        return userRepository.findAllByFilter(filter, resolvedPageable)
                .map(this::toDto);
    }

    public User create(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public User updateById(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setRole(user.getRole());
        existingUser.setActive(user.isActive());

        return userRepository.save(existingUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getRole().getName(),
                user.isActive(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}

package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.dto.UserDto;
import kz.iitu.se242m.yesniyazova.entity.dto.UserFilterDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Page<UserDto> getUsersByFilter(UserFilterDto filter, Pageable pageable);
    User create(User user);
    User updateById(Long id, User user);
    void deleteById(Long id);
}

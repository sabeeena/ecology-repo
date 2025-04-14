package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.UserRole;
import kz.iitu.se242m.yesniyazova.entity.dto.UserDto;
import kz.iitu.se242m.yesniyazova.entity.dto.UserFilterDto;
import kz.iitu.se242m.yesniyazova.service.UserRoleService;
import kz.iitu.se242m.yesniyazova.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/info/")
    public Page<UserDto> getAllUsersByFilter(@RequestBody UserFilterDto filter, Pageable pageable) {
        return userService.getUsersByFilter(filter, pageable);
    }

    @GetMapping("/roles")
    public List<UserRole> getAllRoles() {
        return userRoleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public User getUserInfo(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.updateById(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

}

package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.entity.UserRole;
import kz.iitu.se242m.yesniyazova.repository.UserRoleRepository;
import kz.iitu.se242m.yesniyazova.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }

}

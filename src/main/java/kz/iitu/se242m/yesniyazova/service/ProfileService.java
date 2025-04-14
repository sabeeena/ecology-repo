package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.User;

public interface ProfileService {
    User me(String username);
    User update(String username, User patch);
    void deactivate(String username);
}

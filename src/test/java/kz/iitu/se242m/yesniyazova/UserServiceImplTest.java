package kz.iitu.se242m.yesniyazova;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.repository.UserRepository;
import kz.iitu.se242m.yesniyazova.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        assertFalse(userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertTrue(userService.getUserById(1L).isPresent());
    }

    @Test
    void create_persistsUser() {
        User toSave = new User(); toSave.setUsername("bob");
        when(userRepository.save(toSave)).thenReturn(toSave);

        User saved = userService.create(toSave);

        assertThat(saved.getUsername()).isEqualTo("bob");
        verify(userRepository).save(toSave);
    }

    @Test
    void updateById_overwritesIdAndSaves() {
        User incoming = new User(); incoming.setUsername("upd");
        when(userRepository.save(incoming)).thenReturn(incoming);

        User updated = userService.updateById(8L, incoming);

        assertThat(updated.getId()).isEqualTo(8L);
        verify(userRepository).save(incoming);
    }

    @Test
    void deleteById_delegatesToUserRepository() {
        userService.deleteById(9L);
        verify(userRepository).deleteById(9L);
    }
}

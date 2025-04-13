package kz.iitu.se242m.yesniyazova;

import kz.iitu.se242m.yesniyazova.entity.UserRole;
import kz.iitu.se242m.yesniyazova.repository.UserRoleRepository;
import kz.iitu.se242m.yesniyazova.service.impl.UserRoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    public UserRoleServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRoles() {
        when(userRoleRepository.findAll()).thenReturn(Arrays.asList(new UserRole(), new UserRole()));
        assertEquals(2, userRoleService.getAllRoles().size());
    }
}
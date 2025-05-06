package kz.iitu.se242m.yesniyazova.seed;

import jakarta.annotation.PostConstruct;
import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.UserRole;
import kz.iitu.se242m.yesniyazova.repository.UserRepository;
import kz.iitu.se242m.yesniyazova.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * For test purposes only - not intended for production
 */

@Component
@RequiredArgsConstructor
public class UserSeeder {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seed() {
        UserRole adminRole = userRoleRepository.findByCode("ADMIN")
                .orElseGet(() -> {
                    UserRole role = new UserRole();
                    role.setCode("ADMIN");
                    role.setName("Administrator");
                    return userRoleRepository.save(role);
                });

        UserRole userRole = userRoleRepository.findByCode("USER")
                .orElseGet(() -> {
                    UserRole role = new UserRole();
                    role.setCode("USER");
                    role.setName("User");
                    return userRoleRepository.save(role);
                });

        if (userRepository.count() > 0) return;

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Root");
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPhoneNumber("7000000001");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(adminRole);
        admin.setActive(true);
        userRepository.save(admin);

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPhoneNumber("7000000002");
        user1.setPassword(passwordEncoder.encode("user1"));
        user1.setRole(userRole);
        user1.setActive(true);
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setPhoneNumber("7000000003");
        user2.setPassword(passwordEncoder.encode("user2"));
        user2.setRole(userRole);
        user2.setActive(true);
        userRepository.save(user2);
    }
}

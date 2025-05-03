package kz.iitu.se242m.yesniyazova;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kz.iitu.se242m.yesniyazova.client.OpenWeatherClient;
import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.UserRole;
import kz.iitu.se242m.yesniyazova.entity.dto.AuthResponse;
import kz.iitu.se242m.yesniyazova.entity.dto.UserDto;
import kz.iitu.se242m.yesniyazova.entity.dto.UserFilterDto;
import kz.iitu.se242m.yesniyazova.repository.AirSampleRepository;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import kz.iitu.se242m.yesniyazova.repository.UserRepository;
import kz.iitu.se242m.yesniyazova.repository.UserRoleRepository;
import kz.iitu.se242m.yesniyazova.security.JwtUtil;
import kz.iitu.se242m.yesniyazova.service.impl.AirQualityServiceImpl;
import kz.iitu.se242m.yesniyazova.service.impl.AuthServiceImpl;
import kz.iitu.se242m.yesniyazova.service.impl.ProfileServiceImpl;
import kz.iitu.se242m.yesniyazova.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ApplicationScenarioTests {

    /* ---------- Auth ---------- */
    @Nested
    class AuthTests {

        @Mock
        UserRepository userRepo;

        @Mock
        UserRoleRepository roleRepo;

        @Mock
        JwtUtil jwt;

        @Mock
        BCryptPasswordEncoder encoder;

        AuthServiceImpl svc;

        @BeforeEach
        void init() {
            svc = new AuthServiceImpl(userRepo, roleRepo, encoder, jwt);
        }

        /**
         * REGISTRATION TEST
         */
        @Test
        void register_user_success() throws JsonProcessingException {
            UserRole userRole = new UserRole();
            userRole.setCode("USER");

            when(roleRepo.findByCode("USER")).thenReturn(Optional.of(userRole));
            when(userRepo.existsByUsername("bob123")).thenReturn(false);
            when(userRepo.existsByEmail("bob@example.com")).thenReturn(false);
            when(encoder.encode("pwd")).thenReturn("hash");

            User in = new User();
            in.setFirstName("Bob");
            in.setLastName("Johnson");
            in.setUsername("bob123");
            in.setEmail("bob@example.com");
            in.setPhoneNumber("+77011234567");
            in.setPassword("pwd");

            System.out.println("Before registration: ");
            printJson(in);
            System.out.println();

            svc.registerUser(in);

            System.out.println("After registration:");
            printJson(in);

            assertThat(in.getRole()).isEqualTo(userRole);
            assertThat(in.getPassword()).isEqualTo("hash");
            verify(userRepo).save(in);
        }

        /**
         * LOGIN TOKEN RETRIEVAL TEST
         */
        @Test
        void login_returns_token() {
            User u = new User();
            u.setUsername("bob");
            u.setPassword("hash");

            UserRole r = new UserRole();
            r.setCode("ADMIN");

            u.setRole(r);

            when(userRepo.findByUsername("bob")).thenReturn(Optional.of(u));
            when(encoder.matches("pwd", "hash")).thenReturn(true);
            when(jwt.generateToken("bob", "ADMIN")).thenReturn("TOKEN");

            AuthResponse res = svc.login("bob", "pwd");
            System.out.println("Token generated: " + res.token());

            assertThat(res.token()).isEqualTo("TOKEN");
        }
    }

    /* ---------- Profile ---------- */
    @Nested
    class ProfileTests {

        @Mock UserRepository repo;
        ProfileServiceImpl svc;

        @BeforeEach void init() { svc = new ProfileServiceImpl(repo); }

        /**
         * TEST USER UPDATE
         */
        @Test void update_changes_fields() {
            User existing = new User();
            existing.setUsername("alice");
            existing.setFirstName("OldName");
            existing.setEmail("old@email.com");

            when(repo.findByUsername("alice")).thenReturn(Optional.of(existing));

            User patch = new User();
            patch.setFirstName("A");
            patch.setEmail("x@y.z");

            User saved = new User();
            saved.setFirstName("A");
            saved.setEmail("x@y.z");

            when(repo.save(any())).thenReturn(saved);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            System.out.println("Before update:");
            printJson(existing);
            System.out.println();

            User result = svc.update("alice", patch);

            System.out.println("After update:");
            printJson(result);

            assertThat(result.getFirstName()).isEqualTo("A");
            verify(repo).save(existing);
        }

        /**
         * TEST USER DEACTIVATION
         */
        @Test void deactivate_sets_active_false() {
            User u = new User(); u.setActive(true);
            when(repo.findByUsername("bob")).thenReturn(Optional.of(u));

            svc.deactivate("bob");

            assertThat(u.isActive()).isFalse();
            verify(repo).save(u);
        }
    }

    /* ---------- Air Quality ---------- */
    @Nested
    class AirQualityTests {

        @Mock
        CityRepository cityRepo;
        @Mock
        AirSampleRepository airRepo;
        @Mock
        OpenWeatherClient ow;

        AirQualityServiceImpl svc;

        City almaty;
        OpenWeatherClient.AirRecord.Entry entry;

        @BeforeEach void init() {
            svc = new AirQualityServiceImpl();
            MockitoAnnotations.openMocks(svc);
            setPrivateField(svc, "cityRepository", cityRepo);
            setPrivateField(svc, "airSampleRepository", airRepo);
            setPrivateField(svc, "openWeatherClient", ow);

            almaty = new City("Almaty", 43.2, 76.8);
            entry = new OpenWeatherClient.AirRecord.Entry();
            entry.main = new OpenWeatherClient.AirRecord.Entry.Main(); entry.main.aqi = 2;
            entry.components = Map.of("co", 100.0, "pm2_5", 5.5);
            entry.dt = Instant.now().getEpochSecond();
        }

        /**
         * OPEN WEATHER DATA RETRIEVAL TEST
         */
        @Test void pullCurrent_saves_sample() {
            when(cityRepo.findAll()).thenReturn(List.of(almaty));
            var rec = new OpenWeatherClient.AirRecord(); rec.list = List.of(entry);
            when(ow.fetchNow(almaty)).thenReturn(rec);

            svc.pullCurrent();

            verify(airRepo, times(1)).save(any(AirSample.class));
        }
    }

    /* ---------- User Tests ---------- */
    @Nested
    class UserServiceTests {

        @Mock UserRepository repo;
        UserServiceImpl svc;

        @BeforeEach void init() { svc = new UserServiceImpl(); setPrivateField(svc, "userRepository", repo); }

        /**
         * ADMIN PANEL - USER PAGEABLE TEST
         */
        @Test void filter_returns_page() {
            Page<User> empty = new PageImpl<>(List.of());
            when(repo.findAllByFilter(any(), any())).thenReturn(empty);

            Page<UserDto> res = svc.getUsersByFilter(new UserFilterDto(), PageRequest.of(0, 10));

            assertThat(res.getTotalElements()).isZero();
        }
    }

    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printJson(Object o) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            Arrays.stream(json.split("\n")).forEach(line -> System.out.println("   " + line));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

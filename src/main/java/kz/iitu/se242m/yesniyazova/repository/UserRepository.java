package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.entity.dto.UserFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findAllByIsActiveTrue();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE UPPER(u.role) = UPPER(:role)")
    List<User> findAllByRole(@Param("role") String role);

    @Query(value = "SELECT * FROM users WHERE UPPER(username) LIKE UPPER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<User> searchByUsernameFragment(String fragment);

    @Query("SELECT u FROM User u " +
            "WHERE (:#{#filter.username} IS NULL OR LOWER(u.username) = LOWER(:#{#filter.username})) " +
            "AND (:#{#filter.email} IS NULL OR LOWER(u.email) = LOWER(:#{#filter.email})) " +
            "AND (:#{#filter.role} IS NULL OR LOWER(u.role.name) = LOWER(:#{#filter.role})) " +
            "AND (:#{#filter.isActive} IS NULL OR u.isActive = :#{#filter.isActive}) " +
            "AND (:#{#filter.fullNameFragment} IS NULL OR " +
            "      LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :#{#filter.fullNameFragment}, '%'))) " +
            "AND (:#{#filter.createdAfter} IS NULL OR u.createdAt >= :#{#filter.createdAfter}) " +
            "AND (:#{#filter.createdBefore} IS NULL OR u.createdAt <= :#{#filter.createdBefore})")
    Page<User> findAllByFilter(UserFilterDto filter, Pageable pageable);

}


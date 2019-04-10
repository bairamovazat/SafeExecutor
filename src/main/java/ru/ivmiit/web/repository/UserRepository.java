package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivmiit.web.model.User;
import ru.ivmiit.web.security.details.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUuid(UUID uuid);

    Optional<User> findOneByLogin(String login);

    Optional<User> findById(Long userId);

    Optional<User> findByUuid(UUID uuid);
}

package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.web.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstByRole(ru.ivmiit.web.security.details.Role role);
}

package ru.ivmiit.web.repository;

import ru.ivmiit.web.security.details.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<ru.ivmiit.web.model.Role, Long> {
    ru.ivmiit.web.model.Role findFirstByRole(Role role);
}

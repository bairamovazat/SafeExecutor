package ru.ivmiit.web.repository;

import ru.ivmiit.web.security.details.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<ru.ivmiit.web.model.autorization.Role, Long> {
    ru.ivmiit.web.model.autorization.Role findFirstByRole(Role role);
}

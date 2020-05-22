package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.ExecutableType;
import ru.ivmiit.web.model.Language;

import java.util.List;

public interface ExecutableRepository extends JpaRepository<Executable, Long> {
    List<Executable> findAllByType(ExecutableType type);
}

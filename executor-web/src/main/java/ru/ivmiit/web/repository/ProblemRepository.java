package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.ExecutableType;
import ru.ivmiit.web.model.Problem;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}

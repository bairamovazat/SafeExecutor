package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivmiit.web.model.TaskCategory;

import java.util.Optional;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {

    Optional<TaskCategory> findById(Long id);
}

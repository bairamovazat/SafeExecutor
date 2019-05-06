package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.web.model.TaskSample;

public interface TaskSampleRepository extends JpaRepository<TaskSample, Long>{
}

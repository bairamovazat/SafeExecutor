package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.ivmiit.web.model.TaskTest;

@Repository
public interface TaskTestRepository extends JpaRepository<TaskTest, Long>,
        PagingAndSortingRepository<TaskTest, Long> {

}

package ru.ivmiit.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.ivmiit.web.model.TaskTest;

import java.util.List;

@Repository
public interface TaskTestRepository extends JpaRepository<TaskTest, Long>,
        PagingAndSortingRepository<TaskTest, Long> {

}

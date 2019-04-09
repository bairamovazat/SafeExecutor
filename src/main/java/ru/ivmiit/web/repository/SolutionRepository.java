package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ivmiit.web.model.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long>,
        PagingAndSortingRepository<Solution, Long> {
}

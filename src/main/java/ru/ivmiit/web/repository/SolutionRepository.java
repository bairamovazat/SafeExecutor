package ru.ivmiit.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

import ru.ivmiit.web.model.Solution;
import ru.ivmiit.web.model.User;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Long>,
        PagingAndSortingRepository<Solution, Long> {
    Page<Solution> findAllByAuthorOrderByIdDesc(User user, Pageable pageable);
    Integer countAllByAuthor(User user);
}

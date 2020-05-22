package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.ivmiit.web.model.TestCase;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long>,
        PagingAndSortingRepository<TestCase, Long> {

}

package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.web.model.*;

import java.util.List;
import java.util.Optional;

public interface CompletedTestCaseRepository extends JpaRepository<CompletedTestCase, Long> {
    Optional<CompletedTestCase> findFirstByTestCaseAndSubmission(TestCase testCase, Submission submission);
}

package ru.ivmiit.web.service;

import ru.ivmiit.web.model.Problem;
import ru.ivmiit.web.model.TestCase;
import ru.ivmiit.web.model.autorization.User;
import ru.ivmiit.web.transfer.ProblemDto;
import ru.ivmiit.web.transfer.SubmissionDto;
import ru.ivmiit.web.transfer.TestCaseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemService {
    Problem save(ProblemDto taskForm);

    @Transactional
    List<ProblemDto> getProblemsDto(int page);

    @Transactional
    List<ProblemDto> getProblemsDto(int page, int count);

    List<Integer> getPageList(int currentPage);

    Problem getProblem(Long id);

    ProblemDto getProblemDto(Long id);

    @Transactional
    void saveSubmission(SubmissionDto submissionDto, User user);

    @Transactional
    void saveProblemTestCase(Long taskId, TestCaseDto testCaseDto);

    TestCase getProblemTestCase(Long testCaseId);

    TestCaseDto getProblemTestCaseDto(Long testCaseId);

    void deleteTestCase(Long testCaseId);

    void deleteProblem(Long problemId);
}

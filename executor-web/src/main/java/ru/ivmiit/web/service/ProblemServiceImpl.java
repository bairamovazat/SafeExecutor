package ru.ivmiit.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.model.Problem;
import ru.ivmiit.web.model.Submission;
import ru.ivmiit.web.model.SubmissionStatus;
import ru.ivmiit.web.model.TestCase;
import ru.ivmiit.web.model.autorization.User;
import ru.ivmiit.web.repository.*;
import ru.ivmiit.web.transfer.ProblemDto;
import ru.ivmiit.web.transfer.SubmissionDto;
import ru.ivmiit.web.transfer.TestCaseDto;
import ru.ivmiit.web.utils.TaskUtils;

import java.util.List;
import java.util.Objects;


@Service
public class ProblemServiceImpl implements ProblemService {

    private static final int pagesCount = 5;
    private static final int defaultPageElementCount = 10;

    @Autowired
    private ProblemRepository taskRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ExecutableRepository executableRepository;

    @Override
    @Transactional
    public Problem save(ProblemDto problemDto) {
        Problem problem;
        if (problemDto.getId() != null) {
            problem = problemRepository.findById(problemDto.getId())
                    .orElse(new Problem());
        } else {
            problem = new Problem();
        }
        problem.setName(problemDto.getName());
        problem.setDescription(problemDto.getDescription());
        problem.setTimeLimit(problemDto.getTimeLimit());
        problem.setMemLimit(problemDto.getMemLimit());
        problem.setOutputLimit(problemDto.getOutputLimit());
        if (problemDto.getSpecialRun() != null) {
            problem.setSpecialRun(executableRepository.findById(problemDto.getSpecialRun())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Executable not found, id: " +
                                    problemDto.getSpecialRun())
                    )
            );
        } else {
            problem.setSpecialRun(null);
        }
        if (problemDto.getSpecialCompare() != null) {
            problem.setSpecialCompare(executableRepository.findById(problemDto.getSpecialCompare())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Executable not found, id: " +
                                    problemDto.getSpecialCompare())
                    )
            );
        } else {
            problem.setSpecialCompare(null);
        }
        problem.setSpecialCompareArgs(problemDto.getSpecialCompareArgs());
        problem.setCombinedRunCompare(problemDto.getCombinedRunCompare());
        problem.setInputDescription(problemDto.getInputDescription());
        problem.setOutputDescription(problemDto.getOutputDescription());
        problem = problemRepository.save(problem);
        return problem;
    }

    @Override
    @Transactional
    public List<ProblemDto> getProblemsDto(int page) {
        return getProblemsDto(page, defaultPageElementCount);
    }

    @Override
    @Transactional
    public List<ProblemDto> getProblemsDto(int page, int count) {
        return ProblemDto.from(taskRepository.findAll(PageRequest.of(page, count)).getContent());
    }

    @Override
    public List<Integer> getPageList(int currentPage) {
        int pageCount = (int) Math.ceil(((double) taskRepository.count()) / pagesCount);
        int maxPage = pageCount - 1;
        return TaskUtils.getPageList(currentPage, pagesCount, maxPage);
    }

    @Override
    @Transactional
    public ProblemDto getProblemDto(Long id) {
        return ProblemDto.from(getProblem(id));
    }

    @Override
    @Transactional
    public Problem getProblem(Long id) {
        Problem problem = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not fount"));
        return problem;
    }

    @Transactional
    public void saveSubmission(SubmissionDto submissionDto, User user) {
        Problem problem = getProblem(submissionDto.getProblemId());
        Submission submission = new Submission();
        submission.setProblem(problem);
        submission.setSource(submissionDto.getSource());
        submission.setStatus(SubmissionStatus.CREATED);
        //TODO поменять
        submission.setLanguage(null);
        submission.setAuthor(user);
        submissionRepository.saveAndFlush(submission);

        if (problem.getTestCases().size() == 0) {
            submission.setStatus(SubmissionStatus.EMPTY_TEST);
        }
    }

    @Override
    @Transactional
    public void saveProblemTestCase(Long problemId, TestCaseDto testCaseDto) {
        Problem problem = taskRepository.findById(problemId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        TestCase testCase;

        if (testCaseDto.getId() != null) {
            testCase = testCaseRepository.findById(testCaseDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("TestCase not found"));
        } else {
            testCase = new TestCase();
        }

        if (testCaseDto.getId() != null && !Objects.equals(testCase.getProblem().getId(), problemId)) {
            throw new IllegalArgumentException("Invalid problemId");
        }

        testCase.setProblem(problem);
        testCase.setInputData(testCaseDto.getInputData());
        testCase.setOutputData(testCaseDto.getOutputData());

        testCaseRepository.save(testCase);
    }

    @Override
    public TestCase getProblemTestCase(Long testCaseId) {
        return testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new IllegalArgumentException("Test not found"));
    }

    @Override
    public TestCaseDto getProblemTestCaseDto(Long testCaseId) {
        return TestCaseDto.from(getProblemTestCase(testCaseId));
    }

    @Override
    @Transactional
    public void deleteTestCase(Long testCaseId) {
        testCaseRepository.deleteById(testCaseId);
    }

    @Transactional
    @Override
    public void deleteProblem(Long problemId) {
        problemRepository.deleteById(problemId);
    }

}

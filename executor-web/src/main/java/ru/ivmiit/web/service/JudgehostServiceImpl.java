package ru.ivmiit.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ivmiit.domjudge.connector.service.JudgehostService;
import ru.ivmiit.domjudge.connector.transfer.*;
import ru.ivmiit.domjudge.connector.utils.ConfigInMemoryUtils;
import ru.ivmiit.domjudge.connector.utils.ExecutablesInMemoryUtils;
import ru.ivmiit.web.model.*;
import ru.ivmiit.web.repository.JudgingRepository;
import ru.ivmiit.web.repository.ProblemRepository;
import ru.ivmiit.web.repository.SubmissionRepository;
import ru.ivmiit.web.repository.TestCaseRepository;
import ru.ivmiit.web.utils.EncoderUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JudgehostServiceImpl implements JudgehostService {
    private static HashMap<String, Object> configMap = ConfigInMemoryUtils.getConfig();
    private static HashMap<String, String> executablesMap = ExecutablesInMemoryUtils.getExecutables();
    private static Integer requestCount = -1;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private JudgingRepository judgingRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public Object getConfig(String name) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(name, configMap.get(name));
        return hashMap;
    }

    private Submission getNextSubmission() {
        return submissionRepository.findFirstByStatus(SubmissionStatus.CREATED);
    }

    private Judging createNewJudging(Long submissionId) {
        return transactionTemplate.execute(transactionStatus -> {
            Submission submission = submissionRepository.getOne(submissionId);
            Judging judging = Judging.builder()
                    .submission(submission)
                    .compileSuccess(null)
                    .verified(false)
                    .juryMember(null)
                    .verifyComment(null)
                    .valid(false)
                    .outputCompile(null)
                    .seen(false)
                    .build();
            return judgingRepository.save(judging);
        });
    }

    @Override
    @Transactional
    public List<CodeSourceDto> getSubmissionSourceCode(Long contestId, Long submissionId) {
        Submission submission = submissionRepository.getOne(submissionId);
        return Arrays.asList(CodeSourceDto.builder()
                .submissionFileId(submission.getId().toString())
                .submissionId(submission.getId().toString())
                .filename("Program.java")
                .source(EncoderUtils.encodeBase64(submission.getSource()))
                .build());
    }

    @Override
    @Transactional
    public NextJudginDto nextJudging(String judgehostName) {
        Submission submission = getNextSubmission();
        if (submission == null) {
            return null;
        }
        Judging judging = createNewJudging(submission.getId());

        NextJudginDto nextJudginDto = null;
        Map<String, TestCaseDto> testCaseDtoMap = new HashMap<>();

        AtomicInteger testCaseNumber = new AtomicInteger(1);
        submission.getProblem().getTestCases()
                .forEach(testCase -> {
                    testCaseDtoMap.put(Integer.toString(testCaseNumber.getAndIncrement()), TestCaseDto.builder()
                            .md5sum_input(EncoderUtils.getMd5LowerCase(testCase.getInputData()))
                            .md5sum_output(EncoderUtils.getMd5LowerCase(testCase.getOutputData()))
                            .testcaseId(testCase.getId())
                            .rank(Long.valueOf(testCase.getRank()))
                            .build());
                });

        nextJudginDto = NextJudginDto.builder()
                .submissionId(submission.getId())
                .contestId(1L)
                .teamId(1L)
                .problemId(submission.getProblem().getId())
                .languageName("java")
                .languageExtensions(Arrays.asList("java"))
                .filterCompilerFiles(true)
                .entryPoint(null)
                .originalSubmitId(null)
                .maxRuntime(submission.getProblem().getTimeLimit())
                .memLimit(submission.getProblem().getMemLimit())
                .outputLimit(submission.getProblem().getOutputLimit())
                .run("run")
                .compare("compare")
                .compareArgs(null)
                .compileScript("java_javac_detect")
                .combinedRunCompare(false)
                .compareMd5sum("fbab86c0fb3676d68a99d5d905afd8d5")
                .runMd5sum("7be7891f2fc2c18ebaf0bf023f705b15")
                .compileScriptMd5sum("e8d555b99b24187aca249cfa4c4246b3")
                .judgingId(judging.getId())
                .testCases(testCaseDtoMap)
                .build();
        submission.setStatus(SubmissionStatus.PROCESSED);
        submissionRepository.saveAndFlush(submission);
        return nextJudginDto;
    }

    @Override
    @Transactional
    public void updateJudging(String judgehostName, Long judgingId, UpdateJudgingDto updateJudgingDto) {
        Judging judging = judgingRepository.getOne(judgingId);
        UpdateJudgingDto decodedDto = updateJudgingDto.getDecoded();
        judging.setCompileSuccess(decodedDto.getCompileSuccess().equals("1"));
        judging.setOutputCompile(decodedDto.getOutputCompile());
        log.info("UpdateJudgingDto: " + updateJudgingDto.toString() + ", judgehostName: " + judgehostName + ", judgingId: " + judgingId);
    }

    @Override
    @Transactional
    public String getInputTestCaseId(Long testCaseId) {
        TestCase testCase = testCaseRepository.getOne(testCaseId);
        return "\"" + EncoderUtils.encodeBase64(testCase.getInputData()) + "\"";
    }

    @Override
    public String getOutputTestCaseId(Long testCaseId) {
        TestCase testCase = testCaseRepository.getOne(testCaseId);
        return "\"" + EncoderUtils.encodeBase64(testCase.getOutputData()) + "\"";
    }

    @Override
    public List<JudgehostsDto> registerJudgehosts() {
        return new ArrayList<>();
    }

    @Override
    public String getExecutables(String executableId) {
        return executablesMap.get(executableId);
    }

    @Override
    public Integer internalError(InternalErrorDto internalErrorDto) {
        log.info(internalErrorDto.toString());
        return Math.toIntExact(System.currentTimeMillis() % 1000);
    }

    @Override
    public Integer addJudgingRun(String hostName, Long judgingId, List<AddJudgingRunDto> addJudgingRunDtoList) {
        addJudgingRunDtoList.forEach(addJudgingRunDto -> taskExecutor.execute(createAddJudgingRunRunnable(judgingId, addJudgingRunDto)));
        log.info(addJudgingRunDtoList.stream()
                .map(judgingRun -> judgingRun.getDecoded().toString())
                .collect(Collectors.joining(", ")));
        return Math.toIntExact(System.currentTimeMillis() % 1000);
    }

    private Runnable createAddJudgingRunRunnable(Long judgingId, AddJudgingRunDto addJudgingRunDto) {
        return () -> {
            transactionTemplate.execute(transactionStatus -> {
                SubmissionStatus submissionStatus =
                        SubmissionStatus.getStatusFromDomjudgeStatus(addJudgingRunDto.getRunResult());

                Judging judging = judgingRepository.getOne(judgingId);
                TestCase testCase = testCaseRepository.getOne(addJudgingRunDto.getTestcaseId());

                //TODO как-то криво выглядит. Изменить.
                Submission submission = submissionRepository.findFirstByIdAndLock(judging.getSubmission().getId());
                Problem problem = submission.getProblem();

                if (!testCase.getProblem().getId().equals(problem.getId())) {
                    log.error("TestCase problem id (" + testCase.getProblem().getId() + ")" +
                            " not equals to testcaseId(" + problem.getId() + ")");
                }

                List<TestCase> testCases = problem.getTestCases();

                if (submissionStatus != SubmissionStatus.CORRECT) {
                    submission.setStatus(submissionStatus);
                    return submission;
                }
                submission.getPassedTestCases().add(testCase);

                if (testCases.size() == submission.getPassedTestCases().size()) {
                    List<Long> testCasesIdList = problem.getTestCases()
                            .stream()
                            .map(TestCase::getId)
                            .collect(Collectors.toList());

                    List<Long> passedTestIdList = submission.getPassedTestCases()
                            .stream()
                            .map(TestCase::getId)
                            .collect(Collectors.toList());
                    if (passedTestIdList.containsAll(testCasesIdList)) {
                        submission.setStatus(SubmissionStatus.CORRECT);
                    } else {
                        log.error("Unforeseen situation arrays not equals," +
                                " testCasesIdList: " + testCasesIdList.toString()
                                + " passedTestIdList: " + passedTestIdList.toString());
                    }
                }
                return submission;
            });
        };
    }
}

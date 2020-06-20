package ru.ivmiit.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ivmiit.domjudge.connector.service.JudgehostService;
import ru.ivmiit.domjudge.connector.transfer.*;
import ru.ivmiit.domjudge.connector.utils.Base64Utils;
import ru.ivmiit.domjudge.connector.utils.ConfigInMemoryUtils;
import ru.ivmiit.web.model.InternalError;
import ru.ivmiit.web.model.*;
import ru.ivmiit.web.repository.*;
import ru.ivmiit.web.utils.EncoderUtils;
import ru.ivmiit.web.utils.JudgingRunDtoUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JudgehostServiceImpl implements JudgehostService {
    private static HashMap<String, Object> configMap = ConfigInMemoryUtils.getConfig();
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
    private CompletedTestCaseRepository completedTestCaseRepository;

    @Autowired
    private ExecutableRepository executableRepository;

    @Override
    public Map<String, Object> getConfig(String name) {
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
                .filename(submission.getFileName())
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

        AtomicInteger testCaseNumber = new AtomicInteger(0);
        submission.getProblem().getTestCases()
                .forEach(testCase -> {
                    testCaseDtoMap.put(Integer.toString(testCaseNumber.incrementAndGet()), TestCaseDto.builder()
                            .md5sum_input(EncoderUtils.getMd5LowerCase(testCase.getInputData()))
                            .md5sum_output(EncoderUtils.getMd5LowerCase(testCase.getOutputData()))
                            .testcaseId(testCase.getId())
                            .rank((long) testCaseNumber.get())
                            .build());
                });

        Language language = submission.getLanguage();
        Problem problem = submission.getProblem();
        Executable compile = language.getCompileScript();

        Executable compare = problem.getSpecialCompare() == null ?
                executableRepository.findFirstByName("compare").orElseThrow(
                        () -> new IllegalArgumentException("Compare executable not found")
                ) :
                problem.getSpecialCompare();

        Executable run = problem.getSpecialRun() == null ?
                executableRepository.findFirstByName("run").orElseThrow(
                        () -> new IllegalArgumentException("Run executable not found")
                ) :
                problem.getSpecialRun();

        nextJudginDto = NextJudginDto.builder()
                .submissionId(submission.getId())
                .contestId(1L)
                .teamId(1L)
                .problemId(submission.getProblem().getId())
                .languageName(language.getName())
                .languageExtensions(language.getExtensions())
                .filterCompilerFiles(language.getFilterCompilerFiles() == null ? false : language.getFilterCompilerFiles())
                .entryPoint(null)
                .originalSubmitId(null)
                .maxRuntime(submission.getProblem().getTimeLimit())
                .memLimit(submission.getProblem().getMemLimit())
                .outputLimit(submission.getProblem().getOutputLimit())
                .run(run.getName())
                .compare(compare.getName())
                .compileScript(compile.getName())
                .compareArgs(null)
                .combinedRunCompare(false)
                .compareMd5sum(compare.getMd5sum())
                .runMd5sum(run.getMd5sum())
                .compileScriptMd5sum(compile.getMd5sum())
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
        String base64 = Base64.getEncoder().encodeToString(testCase.getInputData().getBytes(StandardCharsets.UTF_8));
        return "\"" + base64 + "\"";
    }

    @Override
    public String getOutputTestCaseId(Long testCaseId) {
        TestCase testCase = testCaseRepository.getOne(testCaseId);
        String base64 = Base64.getEncoder().encodeToString(testCase.getOutputData().getBytes(StandardCharsets.UTF_8));
        return "\"" + base64 + "\"";
    }

    @Override
    public List<JudgehostsDto> registerJudgehosts() {
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public String getExecutables(String executableId) {
        Executable executable = executableRepository.findFirstByName(executableId)
                .orElseThrow(() -> new IllegalArgumentException(executableId + " executable not found"));
        String base64Data = new String(Base64.getEncoder().encode(executable.getFileData()));
        return "\"" + base64Data + "\"";
    }

    @Override
    @Transactional
    public Integer internalError(InternalErrorDto internalErrorDto) {
        Judging judging = judgingRepository.getOne(internalErrorDto.getJudgingid());
        Submission submission = submissionRepository.findFirstByIdAndLock(judging.getSubmission().getId());
        submission.setStatus(SubmissionStatus.INTERNAL_ERROR);
        InternalError internalError = InternalError.builder()
                .description(internalErrorDto.getDescription())
                .judgehostLog(Base64Utils.decodeFromUrl(internalErrorDto.getJudgehostlog()))
                .disabled(internalErrorDto.getDisabled())
                .build();
        submission.setInternalError(internalError);
        return Math.toIntExact(System.currentTimeMillis() % 1000);
    }

    @Override
    public Integer addJudgingRun(String hostName, Long judgingId, List<AddJudgingRunDto> addJudgingRunDtoList) {
        addJudgingRunDtoList.forEach(addJudgingRunDto -> taskExecutor.execute(createAddJudgingRunRunnable(judgingId, addJudgingRunDto)));
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

                CompletedTestCase completedTestCase = completedTestCaseRepository.findFirstByTestCaseAndSubmission(testCase, submission)
                        .orElse(new CompletedTestCase());
                completedTestCase.setTestCase(testCase);
                completedTestCase.setSubmission(submission);
                completedTestCase.setSuccess(submissionStatus == SubmissionStatus.CORRECT);
                submission.getCompletedTestCases().add(completedTestCase);

                if (submissionStatus != SubmissionStatus.CORRECT) {
                    JudgingRunDtoUtils.fillFillDataFrom(addJudgingRunDto, completedTestCase);
                    submission.setStatus(submissionStatus);
                    return submission;
                }

                List<TestCase> testCases = problem.getTestCases();

                Set<Long> passedTestIdList = submission.getCompletedTestCases()
                        .stream()
                        .map(e -> e.getTestCase().getId())
                        .collect(Collectors.toSet());

                if (testCases.size() == passedTestIdList.size()) {
                    List<Long> testCasesIdList = problem.getTestCases()
                            .stream()
                            .map(TestCase::getId)
                            .collect(Collectors.toList());
                    ;
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

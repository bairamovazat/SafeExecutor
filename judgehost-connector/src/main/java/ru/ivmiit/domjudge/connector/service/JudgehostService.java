package ru.ivmiit.domjudge.connector.service;

import ru.ivmiit.domjudge.connector.transfer.*;

import java.util.List;

public interface JudgehostService {
    Object getConfig(String name);

    List<CodeSourceDto> getSubmissionSourceCode(Long contestId, Long submissionId);

    NextJudginDto nextJudging(String judgehostName);

    void updateJudging(String judgehostName, Long judgingId, UpdateJudgingDto updateJudgingDto);

    String getInputTestCaseId(Long testCaseId);

    String getOutputTestCaseId(Long testCaseId);

    List<JudgehostsDto> registerJudgehosts();

    String getExecutables(String executableId);

    Integer internalError(InternalErrorDto internalErrorDto);

    Integer addJudgingRun(String hostName, Long judgingId, AddJudgingRunDto addJudgingRunDto);
}

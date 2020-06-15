package ru.ivmiit.domjudge.connector.service;

import ru.ivmiit.domjudge.connector.transfer.*;

import java.util.List;
import java.util.Map;

public interface JudgehostService {
    Map<String, Object> getConfig(String name);

    List<CodeSourceDto> getSubmissionSourceCode(Long contestId, Long submissionId);

    NextJudginDto nextJudging(String judgehostName);

    void updateJudging(String judgehostName, Long judgingId, UpdateJudgingDto updateJudgingDto);

    String getInputTestCaseId(Long testCaseId);

    String getOutputTestCaseId(Long testCaseId);

    List<JudgehostsDto> registerJudgehosts();

    String getExecutables(String executableId);

    Integer internalError(InternalErrorDto internalErrorDto);

    Integer addJudgingRun(String hostName, Long judgingId, List<AddJudgingRunDto> addJudgingRunDtoList);
}

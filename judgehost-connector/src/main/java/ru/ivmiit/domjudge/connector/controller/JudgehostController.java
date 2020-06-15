package ru.ivmiit.domjudge.connector.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivmiit.domjudge.connector.service.JudgehostService;
import ru.ivmiit.domjudge.connector.transfer.*;
import ru.ivmiit.domjudge.connector.utils.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/judgehost/api/v4")
@Slf4j
public class JudgehostController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JudgehostService judgehostService;

    @PostMapping("/judgehosts")
    public List<JudgehostsDto> judgehosts() {
        return judgehostService.registerJudgehosts();
    }

    @GetMapping("/config")
    public Map<String, Object> config(@RequestParam("name") String name) {
        return judgehostService.getConfig(name);
    }

    @PostMapping("/judgehosts/next-judging/{judgehostName}")
    public ResponseEntity<Object> nextJudging(@PathVariable("judgehostName") String judgehostName) {
        NextJudginDto nextJudginDto = judgehostService.nextJudging(judgehostName);
        if (nextJudginDto == null) {
            return ResponseEntity.ok("\"\"");
        } else {
            return ResponseEntity.ok(nextJudginDto);
        }
    }

    @GetMapping("/contests/{contestId}/submissions/{submissionId}/source-code")
    public List<CodeSourceDto> getSourceCode(@PathVariable("contestId") Long contestId, @PathVariable("submissionId") Long submissionId) {
        return judgehostService.getSubmissionSourceCode(contestId, submissionId);
    }

    @PutMapping(value = "/judgehosts/update-judging/{judgehostName}/{judgingId}",
            consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public void updateJudging(@PathVariable("judgehostName") String judgehostName,
                              @PathVariable("judgingId") Long judgingId,
                              HttpServletRequest httpServletRequest) {
        //Хардкод, т.к Put не поддерживает formData
        String body;
        try {
            body = httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            body = "";
            log.error("Pars request error", e);
        }
        Map<String, String> keyValues = UrlUtils.splitQueryParameter(body);

        UpdateJudgingDto updateJudgingDto = UpdateJudgingDto.builder()
                .compileSuccess(keyValues.get("compile_success"))
                .outputCompile(keyValues.get("output_compile"))
                .entryPoint(keyValues.get("entry_point"))
                .build();
        judgehostService.updateJudging(judgehostName, judgingId, updateJudgingDto);
    }

    @GetMapping(value = "/testcases/{testCaseId}/file/input", produces = "application/json")
    public String getInputTestCaseId(@PathVariable("testCaseId") Long testCaseId) {
        return judgehostService.getInputTestCaseId(testCaseId);
    }

    @GetMapping(value = "/testcases/{testCaseId}/file/output", produces = "application/json")
    public String getOutputTestCaseId(@PathVariable("testCaseId") Long testCaseId) {
        return judgehostService.getOutputTestCaseId(testCaseId);
    }

    @GetMapping(value = "/executables/{id}", produces = "application/json; charset=utf-8")
    public String downloadFile(@PathVariable("id") String executableId) {
        return judgehostService.getExecutables(executableId);
    }

    @PostMapping(value = "/judgehosts/internal-error", consumes = "application/x-www-form-urlencoded")
    public Integer internalError(InternalErrorDto internalErrorDto) {
        return judgehostService.internalError(internalErrorDto);
    }

    @PostMapping(value = "/judgehosts/add-judging-run/{hostname}/{judgingId}",
            consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public Integer addJudgingRun(@PathVariable("hostname") String hostName, @PathVariable("judgingId") Long judgingId,
                                 AddJudgingRunWrapperDto batch) {
        try {
            List<AddJudgingRunDto> list = objectMapper.readValue(batch.getBatch(),
                    new TypeReference<List<AddJudgingRunDto>>() {});
            return judgehostService.addJudgingRun(hostName, judgingId, list);

        } catch (IOException e) {
            log.error("Jackson mapper error", e);
        }
        return 0;
    }
}

package ru.ivmiit.domjudge.connector.controller;

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

import java.util.List;

@RestController
@RequestMapping("/judgehost/api")
public class JudgehostController {

    @Autowired
    private JudgehostService judgehostService;

    @PostMapping("/judgehosts")
    public List<JudgehostsDto> judgehosts() {
        return judgehostService.registerJudgehosts();
    }


    /**
     * GET http://localhost/domjudge/api/config?name=diskspace_error
     * response: {"diskspace_error":1048576}
     * <p>
     * GET http://localhost/domjudge/api/config?name=script_timelimit
     * response: {"script_timelimit":30}
     * <p>
     * GET http://localhost/domjudge/api/config?name=script_memory_limit
     * response: {"script_memory_limit":2097152}
     * <p>
     * GET http://localhost/domjudge/api/config?name=script_filesize_limit
     * response: {"script_filesize_limit":540672}
     * <p>
     * GET http://localhost/domjudge/api/config?name=process_limit
     * response: {"process_limit":64}
     * <p>
     * GET http://localhost/domjudge/api/config?name=output_storage_limit
     * response: {"output_storage_limit":50000}
     * <p>
     * GET http://localhost/domjudge/api/config?name=timelimit_overshoot
     * response: {"timelimit_overshoot":"1s|10%"}
     * <p>
     * GET http://localhost/domjudge/api/config?name=update_judging_seconds
     * response: {"update_judging_seconds":5}
     **/
    @GetMapping("/config")
    public Object config(@RequestParam("name") String name) {
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
                              @PathVariable("judgingId") Long judgingId, UpdateJudgingDto updateJudgingDto) {
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
                                 AddJudgingRunDto addJudgingRunDto) {
        return judgehostService.addJudgingRun(hostName, judgingId, addJudgingRunDto);
    }
}

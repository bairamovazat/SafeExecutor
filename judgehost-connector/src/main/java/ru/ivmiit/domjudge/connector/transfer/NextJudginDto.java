package ru.ivmiit.domjudge.connector.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextJudginDto {
    @JsonProperty(value = "submitid")
    private Long submissionId;

    @JsonProperty(value = "cid")
    private Long contestId;

    @JsonProperty(value = "teamid")
    private Long teamId;

    @JsonProperty(value = "probid")
    private Long problemId;

    @JsonProperty(value = "langid")
    private String languageName;

    @JsonProperty(value = "language_extensions")
    private List<String> languageExtensions;

    @JsonProperty(value = "filter_compiler_files")
    private Boolean filterCompilerFiles;

//    @JsonProperty(value = "rejudgingid")
//    private String rejudgingId;

    @JsonProperty(value = "entry_point")
    private String entryPoint;

    @JsonProperty(value = "origsubmitid")
    private Long originalSubmitId;

    @JsonProperty(value = "maxruntime")
    private Long maxRuntime;

    @JsonProperty(value = "memlimit")
    private Long memLimit;

    @JsonProperty(value = "outputlimit")
    private Long outputLimit;

    @JsonProperty(value = "run")
    private String run;

    @JsonProperty(value = "compare")
    private String compare;

    @JsonProperty(value = "compare_args")
    private String compareArgs;

    @JsonProperty(value = "compile_script")
    private String compileScript;

    @JsonProperty(value = "combined_run_compare")
    private Boolean combinedRunCompare;

    @JsonProperty(value = "compare_md5sum")
    private String compareMd5sum;

    @JsonProperty(value = "run_md5sum")
    private String runMd5sum;

    @JsonProperty(value = "compile_script_md5sum")
    private String compileScriptMd5sum;

    @JsonProperty(value = "judgingid")
    private Long judgingId;

    @JsonProperty(value = "testcases")
    private Map<String, TestCaseDto> testCases;

}

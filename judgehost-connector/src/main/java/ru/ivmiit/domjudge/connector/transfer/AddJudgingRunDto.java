package ru.ivmiit.domjudge.connector.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.domjudge.connector.utils.Base64Utils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddJudgingRunDto {

    @JsonProperty(value ="testcaseid")
    private Long testcaseId;

    @JsonProperty(value ="runresult")
    private String runResult;

    @JsonProperty(value ="runtime")
    private Double runtime;

    @JsonProperty(value ="output_run")
    private String outputRun;

    @JsonProperty(value ="output_error")
    private String outputError;

    @JsonProperty(value ="output_diff")
    private String outputDiff;

    @JsonProperty(value ="output_system")
    private String outputSystem;

    @JsonProperty(value ="metadata")
    private String metaData;

    public AddJudgingRunDto getDecoded() {
        return AddJudgingRunDto.builder()
                .testcaseId(this.getTestcaseId())
                .runResult(this.getRunResult())
                .runtime(this.getRuntime())
                .outputRun(Base64Utils.decode(this.getOutputRun()))
                .outputError(Base64Utils.decode(this.getOutputError()))
                .outputDiff(Base64Utils.decode(this.getOutputDiff()))
                .outputSystem(Base64Utils.decode(this.getOutputSystem()))
                .metaData(Base64Utils.decode(this.getMetaData()))
                .build();
    }

}

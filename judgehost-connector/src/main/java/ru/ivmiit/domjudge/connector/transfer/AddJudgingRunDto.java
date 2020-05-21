package ru.ivmiit.domjudge.connector.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonProperty(value ="output_system")
    private String outputSystem;

    @JsonProperty(value ="metadata")
    private String metaData;

}

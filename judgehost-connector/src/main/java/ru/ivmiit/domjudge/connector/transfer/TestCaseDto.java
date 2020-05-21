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
public class TestCaseDto {
    @JsonProperty(value ="md5sum_input")
    private String md5sum_input;

    @JsonProperty(value ="md5sum_output")
    private String md5sum_output;

    @JsonProperty(value ="testcaseid")
    private Long testcaseId;

    @JsonProperty(value ="rank")
    private Long rank;
}

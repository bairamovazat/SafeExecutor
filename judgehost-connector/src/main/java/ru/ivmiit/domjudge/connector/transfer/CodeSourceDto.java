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
public class CodeSourceDto {

    @JsonProperty(value ="id")
    private String submissionFileId;

    @JsonProperty(value ="submission_id")
    private String submissionId;

    @JsonProperty(value ="filename")
    private String filename;

    @JsonProperty(value ="source")
    private String source;
}

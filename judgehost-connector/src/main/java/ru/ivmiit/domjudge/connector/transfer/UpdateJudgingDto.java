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
public class UpdateJudgingDto {

    @JsonProperty(value ="compile_success")
    private Integer compileSuccess;

    @JsonProperty(value ="output_compile")
    private String outputCompile;

    @JsonProperty(value ="entry_point")
    private String entryPoint;
}

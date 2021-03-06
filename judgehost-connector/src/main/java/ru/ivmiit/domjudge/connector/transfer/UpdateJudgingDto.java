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
public class UpdateJudgingDto {

    @JsonProperty(value ="compile_success")
    private String compileSuccess;

    @JsonProperty(value ="output_compile")
    private String outputCompile;

    @JsonProperty(value ="entry_point")
    private String entryPoint;

    public UpdateJudgingDto getDecoded() {
        return UpdateJudgingDto.builder()
                .compileSuccess(this.getCompileSuccess())
                .outputCompile(Base64Utils.decodeFromUrl(this.getOutputCompile()))
                .entryPoint(this.getEntryPoint())
                .build();
    }
}

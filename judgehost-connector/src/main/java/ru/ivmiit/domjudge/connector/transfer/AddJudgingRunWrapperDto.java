package ru.ivmiit.domjudge.connector.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddJudgingRunWrapperDto {
    @JsonProperty(value ="batch")
    private String batch;
}

package ru.ivmiit.domjudge.connector.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Те, которые ещё не закончены (например judgehost отвалился)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JudgehostsDto {

    @JsonProperty(value = "judgingid")
    private Long judgingId;

    @JsonProperty(value = "submitid")
    private Long submissionId;

    @JsonProperty(value = "cid")
    private Long contestId;

}

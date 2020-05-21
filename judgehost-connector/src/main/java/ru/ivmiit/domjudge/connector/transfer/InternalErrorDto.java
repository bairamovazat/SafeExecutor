package ru.ivmiit.domjudge.connector.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternalErrorDto {

    @JsonProperty(value ="description")
    private String description;

    @JsonProperty(value ="judgehostlog")
    private String judgehostLog;

    @JsonProperty(value ="disabled")
    private String disabled;

    @JsonProperty(value ="cid")
    private Integer cid;

    @JsonProperty(value ="judgingid")
    private Integer judgingId;

}

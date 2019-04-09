package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.forms.SolutionStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionDto {

    private Long id;

    private TaskDto task;

    private String codeImport;
    private String code;

    private SolutionStatus status;
}

package ru.ivmiit.web.forms;

import lombok.Data;

@Data
public class SolutionForm {
    private Long taskId;
    private String codeImport;
    private String code;
}

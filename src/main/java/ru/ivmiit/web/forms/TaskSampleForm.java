package ru.ivmiit.web.forms;

import lombok.Data;

@Data
public class TaskSampleForm {
    private Long testId;
    public String inputData;
    public String outputData;
}
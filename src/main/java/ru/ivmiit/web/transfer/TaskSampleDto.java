package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.TaskSample;
import ru.ivmiit.web.model.TaskTest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskSampleDto {
    private Long id;

    public String inputData;
    public String outputData;

    public static TaskSampleDto from(TaskSample taskTest){
        return TaskSampleDto.builder()
                .id(taskTest.getId())
                .inputData(taskTest.getInputData())
                .outputData(taskTest.getOutputData())
                .build();
    }
}

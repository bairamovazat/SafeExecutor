package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.TaskTest;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTestDto {
    private Long id;

    public String inputData;
    public String outputData;

    public static TaskTestDto from(TaskTest taskTest){
        return TaskTestDto.builder()
                .id(taskTest.getId())
                .inputData(taskTest.getInputData())
                .outputData(taskTest.getOutputData())
                .build();
    }

    public static List<TaskTestDto> from(List<TaskTest> taskTestList){
        return taskTestList.stream().map(TaskTestDto::from).collect(Collectors.toList());
    }
}

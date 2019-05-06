package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.Task;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;

    private String name;
    private String description;

    private Integer maxTime;
    private Integer maxRealTime;
    private Integer maxMemory;
    private Integer complexity;

    private String inputDescription;
    private String outputDescription;

    private String category;

    private List<TaskSampleDto> samples;

    public static TaskDto from(Task task){
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .maxTime(task.getMaxTime())
                .maxRealTime(task.getMaxRealTime())
                .maxMemory(task.getMaxMemory())
                .complexity(task.getComplexity())
                .inputDescription(task.getInputDescription())
                .outputDescription(task.getOutputDescription())
                .category(task.getCategory() != null ? task.getCategory().getName() : "")
                .samples(task.getSampleList().stream().map(TaskSampleDto::from).collect(Collectors.toList()))
                .build();
    }
}

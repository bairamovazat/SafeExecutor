package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.Task;

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

    private TaskTestDto firstTest;

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
                .firstTest(task.getTestList().size() > 0 ? TaskTestDto.from(task.getTestList().get(0)) : null)
                .build();
    }
}

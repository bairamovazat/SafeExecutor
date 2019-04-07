package ru.ivmiit.web.model;

import lombok.*;
import ru.ivmiit.web.forms.TaskForm;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private Integer maxTime;
    private Integer maxRealTime;
    private Integer maxMemory;
    private Integer complexity;

    private String inputDescription;
    private String outputDescription;

    @OneToMany(mappedBy="task", cascade = CascadeType.ALL)
    private List<TaskTest> testList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private TaskCategory category;

    public static Task from(TaskForm form){
        List<TaskTest> taskTestList = TaskTest.from(form.getTestList());
        Task task =  Task.builder()
                .name(form.getName())
                .description(form.getDescription())
                .maxTime(form.getMaxTime())
                .maxRealTime(form.getMaxRealTime())
                .maxMemory(form.getMaxMemory())
                .complexity(form.getComplexity())
                .inputDescription(form.getInputDescription())
                .outputDescription(form.getOutputDescription())
                .build();
        taskTestList.forEach(test -> test.setTask(task));
        task.setTestList(taskTestList);
        return task;
    }
}

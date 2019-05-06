package ru.ivmiit.web.model;

import lombok.*;
import ru.ivmiit.web.forms.TaskForm;
import ru.ivmiit.web.forms.TaskSampleForm;
import ru.ivmiit.web.utils.model.ProblemSampleTest;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_sample")
public class TaskSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    public String inputData;
    public String outputData;


    public static TaskSample from(ProblemSampleTest problemSampleTest){
        return TaskSample.builder()
                .inputData(problemSampleTest.getInput())
                .outputData(problemSampleTest.getOutput())
                .build();
    }

    public static TaskSample from(TaskSampleForm sampleForm){
        return TaskSample.builder()
                .inputData(sampleForm.getInputData())
                .outputData(sampleForm.getOutputData())
                .build();
    }

    public static List<TaskSample> from(List<TaskSampleForm> taskSampleForms){
        return taskSampleForms
                .stream()
                .map(TaskSample::from)
                .collect(Collectors.toList());
    }

    public TaskSample copyValuesAndGet(TaskSampleForm taskSampleForm){
        this.setInputData(taskSampleForm.getInputData());
        this.setOutputData(taskSampleForm.getOutputData());
        return this;
    }

}

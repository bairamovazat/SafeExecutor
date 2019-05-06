package ru.ivmiit.web.model;


import lombok.*;
import ru.ivmiit.web.forms.TaskTestForm;
import ru.ivmiit.web.utils.model.ProblemTest;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_test")
public class TaskTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    public String inputData;
    public String outputData;


    public static TaskTest from(ProblemTest problemTest){
        return TaskTest.builder()
                .inputData(problemTest.getInput())
                .outputData(problemTest.getAnswer())
                .build();
    }

    public static TaskTest from(TaskTestForm testForm){
        return TaskTest.builder()
                .inputData(testForm.getInputData())
                .outputData(testForm.getOutputData())
                .build();
    }

    public static List<TaskTest> from(List<TaskTestForm> taskTestFormList){
        return taskTestFormList
                .stream()
                .map(TaskTest::from)
                .collect(Collectors.toList());
    }
}

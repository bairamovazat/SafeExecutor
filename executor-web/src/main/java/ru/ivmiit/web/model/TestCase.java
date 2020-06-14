package ru.ivmiit.web.model;


import ru.ivmiit.web.utils.model.ProblemTest;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test_case")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="problem_id")
    private Problem problem;

    @Column(name = "input_data")
    public String inputData;

    @Column(name = "output_data")
    public String outputData;

    @OneToMany(mappedBy="testCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompletedTestCase> completedTestCases;

    public static TestCase from(ProblemTest problemTest){
        return TestCase.builder()
                .inputData(problemTest.getInput())
                .outputData(problemTest.getAnswer())
                .build();
    }

}

package ru.ivmiit.web.model;


import ru.ivmiit.web.utils.model.ProblemTest;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "input_data")
    public String inputData;

    @Column(name = "output_data")
    public String outputData;

    public static TestCase from(ProblemTest problemTest){
        return TestCase.builder()
                .inputData(problemTest.getInput())
                .outputData(problemTest.getAnswer())
                .build();
    }

}

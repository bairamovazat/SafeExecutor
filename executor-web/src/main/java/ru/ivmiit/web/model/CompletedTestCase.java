package ru.ivmiit.web.model;


import lombok.*;
import org.hibernate.annotations.Type;
import ru.ivmiit.domjudge.connector.transfer.AddJudgingRunDto;
import ru.ivmiit.domjudge.connector.utils.Base64Utils;
import ru.ivmiit.web.utils.JudgingRunDtoUtils;
import ru.ivmiit.web.utils.model.ProblemTest;

import javax.persistence.*;
import java.util.Map;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "completed_test_case")
public class CompletedTestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="submission_id")
    private Submission submission;

    @ManyToOne
    @JoinColumn(name="test_case_id")
    private TestCase testCase;

    @Column(name = "cpu_time")
    private Double cpuTime;

    @Column(name = "memory_used")
    private Long memoryUsed;

    @Column(name = "success")
    private Boolean success;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "output_run")
    private String outputRun;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "output_diff")
    private String outputDiff;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "output_error")
    private String outputError;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "output_system")
    private String outputSystem;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "meta_data")
    private String metaData;

}

package ru.ivmiit.web.model;

import lombok.*;
import ru.ivmiit.web.model.autorization.Role;
import ru.ivmiit.web.model.autorization.User;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Problem problem;

    @Lob
    @Column
    private String source;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "submission_test_case",
            joinColumns = @JoinColumn(
                    name = "submission_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "test_case_id", referencedColumnName = "id"))
    private List<TestCase> passedTestCases;

    //-1 - все
    //0 - ещё не проверялось или ни один тест не сдан
    private Integer passedTestCount = 0;

}

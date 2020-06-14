package ru.ivmiit.web.model;

import lombok.*;
import org.hibernate.annotations.Type;
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

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
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

    @OneToMany(mappedBy="submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompletedTestCase> completedTestCases;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="internal_error_id")
    private InternalError internalError;

}

package ru.ivmiit.web.model;

import lombok.*;
import ru.ivmiit.web.model.autorization.User;

import javax.persistence.*;

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

//    public static Submission from(SolutionForm solutionForm){
//        return Submission.builder()
//                .source(solutionForm.getCodeImport())
//                .status(SolutionStatus.CREATED)
//                .build();
//    }
//
//    public void setStatusFromString(String status){
//        SolutionStatus solutionStatus = SolutionStatus.fromString(status);
//        if(solutionStatus == null){
//            this.setStatus(SolutionStatus.WRONG_ANSWER);
//        }else{
//            this.setStatus(solutionStatus);
//        }
//    }
}

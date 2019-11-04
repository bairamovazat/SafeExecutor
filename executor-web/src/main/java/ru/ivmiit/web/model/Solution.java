package ru.ivmiit.web.model;

import lombok.*;
import ru.ivmiit.web.forms.SolutionForm;
import ru.ivmiit.web.forms.SolutionStatus;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;
    @Column(length = 512)
    private String codeImport;
    @Column(length = 2048)
    private String code;

    @Enumerated(EnumType.STRING)
    private SolutionStatus status;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    private int currentTest;

    public static Solution from(SolutionForm solutionForm){
        return Solution.builder()
                .code(solutionForm.getCode())
                .codeImport(solutionForm.getCodeImport())
                .status(SolutionStatus.CREATED)
                .build();
    }

    public void setStatusFromString(String status){
        SolutionStatus solutionStatus = SolutionStatus.fromString(status);
        if(solutionStatus == null){
            this.setStatus(SolutionStatus.WRONG_ANSWER);
        }else{
            this.setStatus(solutionStatus);
        }
    }
}

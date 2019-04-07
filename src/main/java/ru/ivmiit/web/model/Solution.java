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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="task_id")
    private Task task;

    private String codeImport;
    private String code;

    @Enumerated(EnumType.STRING)
    private SolutionStatus status;

    private int currentTest;

    public static Solution from(SolutionForm solutionForm){
        return Solution.builder()
                .code(solutionForm.getCode())
                .codeImport(solutionForm.getCodeImport())
                .status(SolutionStatus.CREATED)
                .build();
    }
}

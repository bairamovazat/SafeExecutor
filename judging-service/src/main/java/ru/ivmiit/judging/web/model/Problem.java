package ru.ivmiit.judging.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "problem")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "externalid")
    private String externalId;

    @Column(name = "name")
    private String name;

    @Column(name = "time_limit")
    private Float timeLimit;

    @Column(name = "mem_limit")
    private Integer memLimit;

    @Column(name = "output_limit")
    private Integer outputLimit;

    @Column(name = "special_run", length = 32)
    private String specialRun;

    @Column(name = "special_compare", length = 32)
    private String specialCompare;

    @Column(name = "special_compare_args")
    private String specialCompareArgs;

    @Column(name = "combined_run_compare")
    private Boolean combinedRunCompare;

    @Lob
    @Column(name = "problem_text")
    private String problemText;

    @Lob
    @Column(name = "problemtext_type")
    private String problemTextType;



}

package ru.ivmiit.judging.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "submit_time")
    private Date submitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judgehost_id")
    private JudgeHost judgeHost;

    @JoinColumn(name = "valid")
    private Boolean valid;

//    @Column(name = "rejudgingid")
//    private Long rejudgingid;

    @Column(name = "expected_results")
    private Boolean expectedResults;

    @Column(name = "externalid")
    private String externalId;

    @Column(name = "entry_point")
    private String entry_point;




}

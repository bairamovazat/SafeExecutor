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
@Table(name = "judging_run_output")
public class JudgingRunOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judging_run_id")
    private JudgingRun judgingRun;

    @Lob
    @Column(name = "output_run")
    private String outputRun;

    @Lob
    @Column(name = "output_diff")
    private String outputDiff;

    @Lob
    @Column(name = "output_error")
    private String outputError;

    @Lob
    @Column(name = "output_system")
    private String outputSystem;

    @Lob
    @Column(name = "meta_data")
    private String metaData;

}

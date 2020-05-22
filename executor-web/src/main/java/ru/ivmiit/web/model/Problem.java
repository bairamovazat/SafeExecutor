package ru.ivmiit.web.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "problem")
@ToString(exclude = {"testList"})
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

//    @Column(name = "external_id")
//    private String externalId;

    @Column(name = "time_limit")
    private Float timeLimit;

    @Column(name = "mem_limit")
    private Integer memLimit;

    @Column(name = "output_limit")
    private Integer outputLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_run_id")
    private Executable specialRun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_compare_id")
    private Executable specialCompare;

    @Column(name = "special_compare_args")
    private String specialCompareArgs;

    @Column(name = "combined_run_compare")
    private Boolean combinedRunCompare;

    @Column(name = "input_description", length = 1024)
    private String inputDescription;

    @Column(name = "output_description", length = 1024)
    private String outputDescription;

    @OneToMany(mappedBy="problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestCase> testCases;

}

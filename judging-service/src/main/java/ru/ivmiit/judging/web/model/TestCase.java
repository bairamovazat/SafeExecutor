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
@Table(name = "testcase")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "md5sum_input")
    private String md5sumInput;

    @Column(name = "md5sum_output")
    private String md5sumOutput;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "rank")
    private Integer rank;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "image_type", length = 4)
    private String imageType;

    @Column(name = "sample")
    private Boolean sample;

    @Column(name = "original_input_file_name")
    private String originalInputFileName;

}

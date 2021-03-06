package ru.ivmiit.web.model;

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
@Table(name = "judging")
public class Judging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id")
    private Submission submission;

    @Column(name = "compile_success", length = 32)
    private Boolean compileSuccess;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "jury_member")
    private String juryMember;

    @Column(name = "verify_comment")
    private String verifyComment;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "output_compile")
    private String outputCompile;

    @Column(name = "seen")
    private Boolean seen;
}

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
@Table(name = "submission")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "extensions")
    private String extensions;

    @Column(name = "require_entry_point")
    private Boolean requireEntryPoint;

    @Column(name = "entry_point_description")
    private String entryPointDescription;

    @Column(name = "allow_submit")
    private Boolean allowSubmit;

    @Column(name = "allow_judge")
    private Boolean allowJudge;

    @Column(name = "time_factor")
    private Float timeFactor;

    @Column(name = "compile_script", length = 32)
    private String compileScript;

    @Column(name = "filter_compiler_files", length = 32)
    private Boolean filterCompilerFiles;

}

package ru.ivmiit.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @Column(name = "extensions")
    private List<String> extensions;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compile_executable_id")
    private Executable compileScript;

    @Column(name = "filter_compiler_files")
    private Boolean filterCompilerFiles;

}

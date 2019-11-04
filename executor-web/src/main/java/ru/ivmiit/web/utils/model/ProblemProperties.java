package ru.ivmiit.web.utils.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProblemProperties {

    private String scoring;

    private String notes;
    private String legend;
    private String authorLogin;
    private String language;

    private Long timeLimit;

    private String input;
    private String output;

    private String inputFile;
    private String outputFile;

    private String authorName;

    private List<ProblemSampleTest> sampleTests = new ArrayList<>();

    private String name;
    private String interaction;
    private Long memoryLimit;
    private String tutorial;
}

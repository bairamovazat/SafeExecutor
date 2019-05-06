package ru.ivmiit.web.utils.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProblemSampleTest {

    private String output;
    private String input;
    private String inputFile;
    private String outputFile;
}

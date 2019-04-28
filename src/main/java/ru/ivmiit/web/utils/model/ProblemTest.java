package ru.ivmiit.web.utils.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProblemTest {
    private String input;
    private String answer;
}

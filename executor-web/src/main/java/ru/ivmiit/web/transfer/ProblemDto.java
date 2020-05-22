package ru.ivmiit.web.transfer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.Problem;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDto {

    private Long id;

    private String name;

    private String description;

    private Float timeLimit;

    private Integer memLimit;

    private Integer outputLimit;

    private Long specialRun;

    private Long specialCompare;

    private String specialCompareArgs;

    private Boolean combinedRunCompare;

    private String inputDescription;

    private String outputDescription;

    public static ProblemDto from(Problem problem) {
        return ProblemDto.builder()
                .id(problem.getId())
                .name(problem.getName())
                .description(problem.getDescription())
                .timeLimit(problem.getTimeLimit())
                .memLimit(problem.getMemLimit())
                .outputLimit(problem.getOutputLimit())
                .specialRun(
                        Optional.ofNullable(problem.getSpecialRun())
                                .map(Executable::getId)
                                .orElse(null)
                )
                .specialCompare(
                        Optional.ofNullable(problem.getSpecialCompare())
                                .map(Executable::getId)
                                .orElse(null)
                )
                .specialCompareArgs(problem.getSpecialCompareArgs())
                .combinedRunCompare(problem.getCombinedRunCompare())
                .inputDescription(problem.getInputDescription())
                .outputDescription(problem.getOutputDescription())
                .build();
    }

    public static List<ProblemDto> from(List<Problem> problems) {
        return problems.stream()
                .map(ProblemDto::from)
                .collect(Collectors.toList());
    }
}

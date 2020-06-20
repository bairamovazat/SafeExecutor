package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.SubmissionStatus;
import ru.ivmiit.web.model.Submission;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDto {
    private Long id;

    private Long problemId;

    private String fileName;

    private String source;

    private SubmissionStatus status;

    private Long languageId;

    private String languageName;

    private String problemName;

    private Long authorId;

    public static SubmissionDto from(Submission submission) {
        return SubmissionDto.builder()
                .id(submission.getId())
                .problemId(submission.getProblem().getId())
                .source(submission.getSource())
                .status(submission.getStatus())
                .languageId(submission.getLanguage().getId())
                .languageName(submission.getLanguage().getName())
                .problemName(submission.getProblem().getName())
                .authorId(submission.getAuthor().getId())
                .build();
    }

    public static List<SubmissionDto> from(List<Submission> submissions) {
        return submissions.stream()
                .map(SubmissionDto::from)
                .collect(Collectors.toList());
    }

}

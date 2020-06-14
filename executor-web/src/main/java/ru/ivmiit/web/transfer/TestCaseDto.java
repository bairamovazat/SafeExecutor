package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.Problem;
import ru.ivmiit.web.model.TestCase;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseDto {
    private Long id;

    private Long problemId;

    public String inputData;

    public String outputData;

    public static TestCaseDto from(TestCase testCase){
        return TestCaseDto.builder()
                .id(testCase.getId())
                .problemId(testCase.getProblem().getId())
                .inputData(testCase.getInputData())
                .outputData(testCase.getOutputData())
                .build();
    }

    public static List<TestCaseDto> from(List<TestCase> testCaseList){
        return testCaseList.stream().map(TestCaseDto::from).collect(Collectors.toList());
    }
}

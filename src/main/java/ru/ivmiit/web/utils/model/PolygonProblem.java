package ru.ivmiit.web.utils.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PolygonProblem {

    public static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static String descriptionDir = "documents/description.txt";
    public static String tutorialDir = "documents/description.txt";
    public static String propertiesDir = "statements/russian/problem-properties.json";
    public String problemFileName;
    public String problemDir;

    private Map<String, String> names = new HashMap<>();

    private List<String> documents = new ArrayList<>();

    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer testCount;

    private String inputTestPattern;
    private String answerTestPattern;

    private String checkerSource;
    private String checkerType;

    public String readDescriptionFromFile() throws IOException {
        String descPath = problemDir + "/" + descriptionDir;
        return FileUtils.readFileToString(new File(descPath), Charset.forName("UTF-8"));
    }

    public ProblemProperties readProblemPropertiesFromFile() throws IOException {
        String descPath = problemDir + "/" + propertiesDir;
        return objectMapper.readValue(new File(descPath), ProblemProperties.class);
    }

    public List<ProblemTest> readProblemTestsFromDirectory() throws IOException {
        List<ProblemTest> tests = new ArrayList<>();
        for(int i = 1; i <= testCount; i++){
            tests.add(readProblemTestFromDirectory(i));
        }
        return tests;
    }

    private ProblemTest readProblemTestFromDirectory(int number) throws IOException {
        String input = problemDir + "/" + String.format(this.inputTestPattern, number);
        String answer = problemDir + "/" + String.format(this.answerTestPattern, number);
        String inputData = FileUtils.readFileToString(new File(input), Charset.forName("UTF-8"));
        String answerData = FileUtils.readFileToString(new File(answer), Charset.forName("UTF-8"));
        return ProblemTest.builder().input(inputData).answer(answerData).build();
    }

}

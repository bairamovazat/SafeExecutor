package ru.ivmiit.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ivmiit.web.model.Task;
import ru.ivmiit.web.model.TaskSample;
import ru.ivmiit.web.model.TaskTest;
import ru.ivmiit.web.repository.TaskRepository;
import ru.ivmiit.web.utils.FileUtils;
import ru.ivmiit.web.utils.PolygonXmlParser;
import ru.ivmiit.web.utils.TaskUtils;
import ru.ivmiit.web.utils.model.PolygonProblem;
import ru.ivmiit.web.utils.model.ProblemProperties;
import ru.ivmiit.web.utils.model.ProblemTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolygonServiceImpl implements PolygonService {


    private static long unzipCount = 0;
    @Value("${unzip.dir}")
    private String unzipPolygonDir;

    @Autowired
    private TaskRepository taskRepository;

    public PolygonServiceImpl(@Value("${unzip.dir}") String unzipPolygonDir) {
        File unzipDir = new File(unzipPolygonDir);
        if (!unzipDir.exists()) {
            throw new IllegalArgumentException("Unzip Polygon dir not exist(" + unzipDir + ")!");
        }
//        Arrays.asList(Objects.requireNonNull(unzipDir.listFiles())).forEach(File::delete);
    }

    @Transactional
    @Override
    public Task createAndSaveFromPolygonZip(MultipartFile multipartFile) {
        Task task = createTaskFromPolygonZip(multipartFile);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task createTaskFromPolygonZip(MultipartFile multipartFile) {
        try {
            return createTaskFromPolygonZip(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка извлечения IS" + e.getMessage());
        }
    }

    public Task createTaskFromPolygonZip(InputStream inputStream) {
        String unzipDirectoryPath = TaskUtils.getFullPath(unzipPolygonDir) + "/" + unzipCount++;
        File directory = new File(unzipDirectoryPath);
        if (directory.exists()) {
            unzipDirectoryPath = unzipDirectoryPath + "_" + System.currentTimeMillis();
            directory = new File(unzipDirectoryPath);
        }
        directory.mkdir();

        try {
            FileUtils.unzip(inputStream, unzipDirectoryPath);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка разархивации" + e.getMessage());
        }

        PolygonProblem problem;
        try {
            problem = PolygonXmlParser.parseXml(unzipDirectoryPath + "/" + "problem.xml");
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка обработки problem.xml. " + e.getMessage());
        }

        return createTaskFromPolygonProblem(problem);
    }

    public Task createTaskFromPolygonProblem(PolygonProblem polygonProblem) {

        String taskName = polygonProblem.getNames().get("russian");

        if (taskName == null) {
            throw new IllegalArgumentException("Укажите название задачи на Русском");
        }

        String description;
        try {
            description = polygonProblem.readDescriptionFromFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("Не удалось считать файл с описанием:" + e.getMessage());
        }

        ProblemProperties problemProperties;

        try {
            problemProperties = polygonProblem.readProblemPropertiesFromFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("Не удалось считать файл с параметрами:" + e.getMessage());
        }

        List<ProblemTest> problemTestList;

        try {
            problemTestList = polygonProblem.readProblemTestsFromDirectory();
        } catch (IOException e) {
            throw new IllegalArgumentException("Не удалось считать тесты:" + e.getMessage());
        }


        Task task = Task.builder()
                .name(taskName)
                .description(description)
                .maxTime(polygonProblem.getTimeLimit())
                .maxRealTime(polygonProblem.getTimeLimit() / 100)
                .maxMemory(polygonProblem.getMemoryLimit() / 1024 / 1024)
                .complexity(1)
                .inputDescription(problemProperties.getInput())
                .outputDescription(problemProperties.getOutput())
                .testList(
                        problemTestList.stream()
                        .map(TaskTest::from)
                        .collect(Collectors.toList())
                )
                .sampleList(
                        problemProperties.getSampleTests()
                                .stream()
                                .map(TaskSample::from)
                                .collect(Collectors.toList())
                )
                .build();
        return task;

    }

}

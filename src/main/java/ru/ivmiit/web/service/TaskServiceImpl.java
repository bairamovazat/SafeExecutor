package ru.ivmiit.web.service;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.executor.ExecutorCmd;
import ru.ivmiit.executor.ExecutorEjudge;
import ru.ivmiit.executor.ExecutorResult;
import ru.ivmiit.web.forms.SolutionForm;
import ru.ivmiit.web.forms.SolutionStatus;
import ru.ivmiit.web.forms.TaskForm;
import ru.ivmiit.web.model.Solution;
import ru.ivmiit.web.model.Task;
import ru.ivmiit.web.model.TaskTest;
import ru.ivmiit.web.model.User;
import ru.ivmiit.web.repository.SolutionRepository;
import ru.ivmiit.web.repository.TaskCategoryRepository;
import ru.ivmiit.web.repository.TaskRepository;
import ru.ivmiit.web.utils.FileUtils;
import ru.ivmiit.web.utils.TaskUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class TaskServiceImpl implements TaskService {

    static Logger logger = LoggerFactory.getLogger(TaskService.class.getName());

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    @Autowired
    private SolutionRepository solutionRepository;


    @Value("${execute.dir}")
    private String executeDir;

    @Value("${execute.ejudge}")
    private String pathToEjudgeBin;

    private ExecutorEjudge executorEjudge;

    private static int pagesCount = 5;

    private static int defaultPageElementCount = 10;


    public TaskServiceImpl(@Value("${execute.dir}") String executeDir, @Value("${execute.ejudge}") String pathToEjudgeBin) {
        File file = new File(executeDir);
        if (!file.exists()) {
            throw new IllegalArgumentException("Execute dir not exist(" + executeDir + ")!");
        }
        executorEjudge = new ExecutorEjudge(pathToEjudgeBin);
    }

    @Override
    public Task save(TaskForm taskForm) {
        Task task = Task.from(taskForm);
        if (taskForm.getCategoryId() != null) {
            taskCategoryRepository.findById(taskForm.getCategoryId())
                    .ifPresent(task::setCategory);
        }
        taskRepository.save(task);
        taskRepository.flush();
        return task;
    }

    @Override
    @Transactional
    public List<Task> getTasks(int page) {
        return getTasks(page, defaultPageElementCount);
    }

    @Override
    @Transactional
    public List<Task> getTasks(int page, int count) {
        return taskRepository.findAll(PageRequest.of(page, count)).getContent();
    }

    @Override
    public List<Integer> getPageList(int currentPage) {
        int pageCount = (int) Math.ceil(((double) taskRepository.count()) / pagesCount);
        int maxPage = pageCount - 1;
        return TaskUtils.getPageList(currentPage, pagesCount, maxPage);
    }

    @Override
    @Transactional
    public Task getTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not fount"));
        Hibernate.initialize(task.getTestList());
        return task;
    }

    @Async
    @Override
    public void saveAndCheckSolution(SolutionForm solutionForm, User user) {
        save(solutionForm, user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(SolutionForm solutionForm, User user){
        Task task = getTask(solutionForm.getTaskId());
        Solution solution = Solution.from(solutionForm);
        solution.setTask(task);
        solution.setAuthor(user);
        saveOnNewTransact(solution);

        if(task.getTestList().size() == 0){
            logger.error("Task test size 0: " + task);
            solution.setStatus(SolutionStatus.TEST_ERROR);
            saveOnNewTransact(solution);
            return;
        }

        //TODO Обязательно оформить адекватно!!!
        try {
            solution.setStatus(SolutionStatus.PROCESSED);
            saveOnNewTransact(solution);

            String executeDirectory = TaskUtils.getFullPath(executeDir) + "/" + solution.getId();
            File directory = new File(executeDirectory);
            boolean resultDirectory = directory.mkdir();

            File javaFile = new File(executeDirectory + "/Program.java");
            boolean resultJavaFile = javaFile.createNewFile();

            String program = solution.getCodeImport() + "\npublic class Program {\n" + solution.getCode() + "\n}";

            FileUtils.writeToFile(javaFile.getAbsolutePath(), program, true);

            try {
                executorEjudge.compileJavaFile(
                        javaFile.getAbsolutePath(),
                        directory.getAbsolutePath());
            } catch (IllegalArgumentException e) {
                solution.setStatus(SolutionStatus.COMPILATION_ERROR);
                saveOnNewTransact(solution);
                return;
            }

            String classFilePath = "Program";
            int current_test = 1;
            for (TaskTest test : task.getTestList()) {
                solution.setCurrentTest(current_test);
                saveOnNewTransact(solution);

                ExecutorResult result = executorEjudge.runFile(classFilePath, directory.getAbsolutePath(), test.getInputData(), task.getMaxTime(), task.getMaxMemory(), task.getMaxMemory());

                if(!result.isOk()){
                    solution.setStatusFromString(result.getStatus());
                    saveOnNewTransact(solution);
                    return;
                }else if (!result.resultEquals(test.outputData)) {
                    solution.setStatus(SolutionStatus.WRONG_ANSWER);
                    saveOnNewTransact(solution);
                    return;
                }
            }
            solution.setStatus(SolutionStatus.ACCEPTED);
            saveOnNewTransact(solution);
            return;
        } catch (IOException ignore) {
            solution.setStatus(SolutionStatus.WRONG_ANSWER);
            saveOnNewTransact(solution);
            logger.error("Task test IOException: " + task);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOnNewTransact(Solution solution){
        solutionRepository.saveAndFlush(solution);
    }

}

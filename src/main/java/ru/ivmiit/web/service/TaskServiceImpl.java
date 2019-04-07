package ru.ivmiit.web.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.executor.Executor;
import ru.ivmiit.web.forms.SolutionForm;
import ru.ivmiit.web.forms.SolutionStatus;
import ru.ivmiit.web.forms.TaskForm;
import ru.ivmiit.web.model.Solution;
import ru.ivmiit.web.model.Task;
import ru.ivmiit.web.model.TaskTest;
import ru.ivmiit.web.repository.SolutionRepository;
import ru.ivmiit.web.repository.TaskCategoryRepository;
import ru.ivmiit.web.repository.TaskRepository;
import ru.ivmiit.web.utils.TaskUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("${execute.dir}")
    private String executeDir;

    private static int pagesCount = 5;

    private static int defaultPageElementCount = 5;

    public TaskServiceImpl(@Value("${execute.dir}") String executeDir) {
        File file = new File(executeDir);
        if (!file.exists()) {
            throw new IllegalArgumentException("Execute dir not exist(" + executeDir + ")!");
        }
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

    @Override
    @Transactional
    public void saveAndCheckSolution(SolutionForm solutionForm) {
        Task task = getTask(solutionForm.getTaskId());
        Solution solution = Solution.from(solutionForm);
        solution.setTask(task);
        solutionRepository.saveAndFlush(solution);

        executorService.submit(() -> {
            //TODO Обязательно оформить адекватно!!!
            try {
                String executeDirectory = TaskUtils.getFullPath(executeDir) + "\\" + solution.getId();
                File directory = new File(executeDirectory);
                boolean resultDirectory = directory.mkdir();

                File javaFile = new File(executeDirectory + "\\Program.java");
                boolean resultJavaFile = javaFile.createNewFile();

                String program = solution.getCodeImport() + "\npublic class Program {\n" + solution.getCode() + "\n}";

                PrintWriter out = new PrintWriter(javaFile.getAbsolutePath(), "UTF-8");
                out.print(program);
                out.close();

                try {
                    Executor.compileJavaFile(javaFile.getAbsolutePath(), directory.getAbsolutePath());
                } catch (IllegalArgumentException e) {
                    solution.setStatus(SolutionStatus.COMPILATION_ERROR);
                    solutionRepository.saveAndFlush(solution);
                    return;
                }
                String classFilePath = "Program";
                int current_test = 1;
                for (TaskTest test : task.getTestList()) {
                    solution.setCurrentTest(current_test);
                    solutionRepository.saveAndFlush(solution);

                    List<String> result = Executor.runFile(classFilePath, directory.getAbsolutePath(), test.getInputData());
                    String concatResult = String.join("\n", result);

                    if (concatResult.toLowerCase().contains("error:")) {
                        solution.setStatus(SolutionStatus.WRONG_ANSWER);
                        solutionRepository.saveAndFlush(solution);
                        return;
                    } else if (!concatResult.equals(test.outputData)) {
                        solution.setStatus(SolutionStatus.WRONG_ANSWER);
                        solutionRepository.saveAndFlush(solution);
                        return;
                    }
//
                }
                solution.setStatus(SolutionStatus.ACCEPTED);
                solutionRepository.saveAndFlush(solution);
            } catch (IOException ignore) {
                System.err.println(ignore.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        File file = new File("solutions");
        System.out.println(file.exists());
    }
}

package ru.ivmiit.web.service;

import ru.ivmiit.web.forms.*;
import ru.ivmiit.web.model.*;
import ru.ivmiit.web.repository.*;
import ru.ivmiit.web.transfer.TaskDto;
import ru.ivmiit.web.transfer.TaskSampleDto;
import ru.ivmiit.web.transfer.TaskTestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.utils.FileUtils;
import ru.ivmiit.web.utils.TaskUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    private static int pagesCount = 5;
    private static int defaultPageElementCount = 10;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskCategoryRepository taskCategoryRepository;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private TaskTestRepository taskTestRepository;
    @Autowired
    private TaskSampleRepository taskSampleRepository;
//    private JavaExecutor javaExecutor;


    public TaskServiceImpl() {

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
    public TaskDto getTaskDto(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not fount"));
        return TaskDto.from(task);
    }


    @Override
    @Transactional
    public Task getTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not fount"));
        return task;
    }

    @Async
    @Override
    public void saveAndCheckSolution(SolutionForm solutionForm, User user) {
        save(solutionForm, user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(SolutionForm solutionForm, User user) {
        Task task = getTask(solutionForm.getTaskId());
        Solution solution = Solution.from(solutionForm);
        solution.setTask(task);
        solution.setAuthor(user);
        saveOnNewTransact(solution);

        if (task.getTestList().size() == 0) {
            solution.setStatus(SolutionStatus.TEST_ERROR);
            saveOnNewTransact(solution);
            return;
        }

//        //TODO Обязательно оформить адекватно!!!
//        try {
//            solution.setStatus(SolutionStatus.PROCESSED);
//            saveOnNewTransact(solution);
//
//            String executeDirectory = TaskUtils.getFullPath(executeDir) + "/" + solution.getId();
//            File directory = new File(executeDirectory);
//            boolean resultDirectory = directory.mkdir();
//
//            File javaFile = new File(executeDirectory + "/Program.java");
//            boolean resultJavaFile = javaFile.createNewFile();
//
//            String program = solution.getCodeImport() + "\npublic class Program {\n" + solution.getCode() + "\n}";
//
//            FileUtils.writeToFile(javaFile.getAbsolutePath(), program, true);
//
//
//            ExecutorResult compileResult = javaExecutor.compileFile(
//                    javaFile.getAbsolutePath(),
//                    directory.getAbsolutePath());
//
//            if (!compileResult.isOk()) {
//                solution.setStatus(SolutionStatus.COMPILATION_ERROR);
//                saveOnNewTransact(solution);
//                return;
//            }
//
//
//            String classFilePath = "Program";
//            int current_test = 1;
//            for (TaskTest test : task.getTestList()) {
//                solution.setCurrentTest(current_test);
//                saveOnNewTransact(solution);
//
//                ExecutorResult result = javaExecutor.runFile(classFilePath, directory.getAbsolutePath(), test.getInputData(), task.getMaxTime(), task.getMaxMemory(), task.getMaxMemory());
//
//                if (!result.isOk()) {
//                    solution.setStatusFromString(result.getStatus());
//                    saveOnNewTransact(solution);
//                    return;
//                } else if (!result.resultEquals(test.outputData)) {
//                    solution.setStatus(SolutionStatus.WRONG_ANSWER);
//                    saveOnNewTransact(solution);
//                    return;
//                }
//            }
//            solution.setStatus(SolutionStatus.ACCEPTED);
//            saveOnNewTransact(solution);
//            return;
//        } catch (IOException ignore) {
//            solution.setStatus(SolutionStatus.WRONG_ANSWER);
//            saveOnNewTransact(solution);
//        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOnNewTransact(Solution solution) {
        solutionRepository.saveAndFlush(solution);
    }


    @Override
    @Transactional
    public void saveTaskTest(Long taskId, TaskTestForm taskTestForm){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        TaskTest taskTest = TaskTest.from(taskTestForm);
        taskTest.setTask(task);

        if(taskTestForm.getTestId() != null){
            taskTest = taskTestRepository.findById(taskTestForm.getTestId())
                    .orElseThrow(() -> new IllegalArgumentException("Test not found"));
            taskTest.copyValuesAndGet(taskTestForm);
        }

        taskTestRepository.save(taskTest);
    }

    @Override
    @Transactional
    public void saveTaskSample(Long taskId, TaskSampleForm taskSampleForm){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        TaskSample taskSample = TaskSample.from(taskSampleForm);
        taskSample.setTask(task);

        if(taskSampleForm.getTestId() != null){
            taskSample = taskSampleRepository.findById(taskSampleForm.getTestId())
                    .orElseThrow(() -> new IllegalArgumentException("Sample not found"));
            taskSample.copyValuesAndGet(taskSampleForm);
        }

        taskSampleRepository.save(taskSample);
    }


    @Override
    public TaskSample getTaskSample(Long taskId){
        return taskSampleRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Sample not found"));
    }

    @Override
    public TaskTest getTaskTest(Long taskId){
        return taskTestRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Test not found"));
    }

    @Override
    public TaskSampleDto getTaskSampleDto(Long taskId){
        return TaskSampleDto.from(getTaskSample(taskId));
    }

    @Override
    public TaskTestDto getTaskTestDto(Long taskId){
        return TaskTestDto.from(getTaskTest(taskId));
    }

    @Override
    @Transactional
    public void deleteTest(Long taskId){
        taskTestRepository.deleteById(taskId);
    }

    @Override
    public void deleteSample(Long taskId){
        taskSampleRepository.deleteById(taskId);
    }
}

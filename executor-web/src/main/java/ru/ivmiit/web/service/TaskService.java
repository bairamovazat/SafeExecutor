package ru.ivmiit.web.service;

import ru.ivmiit.web.model.Task;
import ru.ivmiit.web.model.TaskSample;
import ru.ivmiit.web.model.TaskTest;
import ru.ivmiit.web.model.User;
import ru.ivmiit.web.transfer.TaskDto;
import ru.ivmiit.web.transfer.TaskSampleDto;
import ru.ivmiit.web.transfer.TaskTestDto;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.forms.SolutionForm;
import ru.ivmiit.web.forms.TaskForm;
import ru.ivmiit.web.forms.TaskSampleForm;
import ru.ivmiit.web.forms.TaskTestForm;

import java.util.List;

public interface TaskService {
    Task save(TaskForm taskForm);

    @Transactional
    List<Task> getTasks(int page);

    @Transactional
    List<Task> getTasks(int page, int count);

    List<Integer> getPageList(int currentPage);

    Task getTask(Long id);

    TaskDto getTaskDto(Long id);

    @Transactional
    void saveAndCheckSolution(SolutionForm solutionForm, User user);

    @Transactional
    void saveTaskTest(Long taskId, TaskTestForm taskTestForm);

    @Transactional
    void saveTaskSample(Long taskId, TaskSampleForm taskSampleForm);

    TaskSample getTaskSample(Long taskId);

    TaskTest getTaskTest(Long taskId);

    TaskSampleDto getTaskSampleDto(Long taskId);

    TaskTestDto getTaskTestDto(Long taskId);

    void deleteTest(Long taskId);

    void deleteSample(Long taskId);
}

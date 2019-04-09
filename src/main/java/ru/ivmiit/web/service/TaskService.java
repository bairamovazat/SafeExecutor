package ru.ivmiit.web.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.forms.SolutionForm;
import ru.ivmiit.web.forms.TaskForm;
import ru.ivmiit.web.model.Solution;
import ru.ivmiit.web.model.Task;
import ru.ivmiit.web.model.User;

import java.util.List;

public interface TaskService {
    Task save(TaskForm taskForm);

    @Transactional
    List<Task> getTasks(int page);

    @Transactional
    List<Task> getTasks(int page, int count);

    List<Integer> getPageList(int currentPage);

    Task getTask(Long id);

    @Transactional
    void saveAndCheckSolution(SolutionForm solutionForm, User user);
}

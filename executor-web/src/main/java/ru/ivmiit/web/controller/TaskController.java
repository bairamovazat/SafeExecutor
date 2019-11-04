package ru.ivmiit.web.controller;

import ru.ivmiit.web.validators.SolutionFormValidator;
import ru.ivmiit.web.validators.TaskFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ivmiit.web.forms.SolutionForm;
import ru.ivmiit.web.forms.TaskForm;
import ru.ivmiit.web.forms.TaskTestForm;
import ru.ivmiit.web.model.Task;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.TaskService;
import ru.ivmiit.web.transfer.TaskDto;
import ru.ivmiit.web.transfer.TaskTestDto;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("tasks/")
public class TaskController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskFormValidator taskFormValidator;

    @Autowired
    private SolutionFormValidator solutionFormValidator;

    @InitBinder("taskForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(taskFormValidator);
    }

    @InitBinder("solutionForm")
    public void initSolutionFormValidator(WebDataBinder binder) {
        binder.addValidators(solutionFormValidator);
    }


    @GetMapping("create")
    public String getTaskPage(@ModelAttribute("model") ModelMap model, Authentication authentication) {
        authenticationService.putUserToModelIfExists(authentication, model);
        return "tasks/create_task";
    }

    @PostMapping("create")
    public String createTask(@Valid @ModelAttribute("taskForm") TaskForm taskForm, BindingResult errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            List<String> errorList = errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            attributes.addFlashAttribute("errors", errorList);
            return "redirect:create";
        }
        taskService.save(taskForm);

        attributes.addFlashAttribute("info", "Успешно!");
        return "redirect:create";
    }

    @GetMapping("all")
    public String getAllTaskPage(@ModelAttribute("model") ModelMap model, Authentication authentication, @RequestParam("page") Optional<Integer> page) {
        authenticationService.putUserToModelIfExists(authentication, model);
        int currentPage = page.orElse(0);
        model.addAttribute("tasks", taskService.getTasks(currentPage));
        model.addAttribute("pageList", taskService.getPageList(currentPage));
        model.addAttribute("currentPage", currentPage);
        return "tasks/all_tasks";
    }

    @GetMapping("{id}")
    public String getTaskPage(@ModelAttribute("model") ModelMap model, Authentication authentication, @PathVariable("id") Optional<Long> taskId) {
        authenticationService.putUserToModelIfExists(authentication, model);
        try {
            TaskDto task = taskService.getTaskDto(taskId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
            model.addAttribute("task", task);
        } catch (IllegalArgumentException e) {
            return "redirect:all";
        }
        return "tasks/task_page";
    }

    @PostMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public String createSolution(@Valid @ModelAttribute("solutionForm") SolutionForm solutionForm,
                                 BindingResult errors,
                                 RedirectAttributes attributes,
                                 Authentication authentication,
                                 @PathVariable("id") Optional<Long> taskId) {
        if (errors.hasErrors()) {
            List<String> errorList = errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            attributes.addFlashAttribute("errors", errorList);
            return "redirect:" + (taskId.isPresent() ? taskId.get() : "/");
        }

        try {
            taskService.saveAndCheckSolution(solutionForm, authenticationService.getUserByAuthentication(authentication));
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        return "redirect:/profile/solutions/all";
    }

    @GetMapping("{id}/test/all")
    @Transactional
    public String getTestPage(@ModelAttribute("model") ModelMap model, Authentication authentication, @PathVariable("id") Optional<Long> taskId) {
        authenticationService.putUserToModelIfExists(authentication, model);
        try {
            Task task = taskService.getTask(taskId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
            model.addAttribute("task", TaskDto.from(task));
            model.addAttribute("tests", TaskTestDto.from(task.getTestList()));
        } catch (IllegalArgumentException e) {
            return "redirect:all";
        }
        return "tasks/tests_page";
    }

    @GetMapping("{id}/test/create")
    @Transactional
    public String getCreateTestPage(@ModelAttribute("model") ModelMap model,
                                    Authentication authentication,
                                    @PathVariable("id") Optional<Long> taskId,
                                    @RequestParam("testId") Optional<Long> testId) {
        authenticationService.putUserToModelIfExists(authentication, model);
        try {
            Task task = taskService.getTask(taskId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
            model.addAttribute("task", TaskDto.from(task));
            testId.ifPresent((id) -> model.addAttribute("test", taskService.getTaskTest(id)));
        } catch (IllegalArgumentException e) {
            return "redirect:all";
        }

        return "tasks/create_test";
    }

    @PostMapping("{id}/test/create")
    @Transactional
    public String createTest(@ModelAttribute("model") ModelMap model,
                             @PathVariable("id") Optional<Long> taskId,
                             @ModelAttribute("taskTestForm") TaskTestForm taskTestForm,
                             BindingResult errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            List<String> errorList = errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            attributes.addFlashAttribute("errors", errorList);
            return "redirect:create";
        }

        try {
            taskService.saveTaskTest(taskId.orElseThrow(() -> new IllegalArgumentException("Id not found")), taskTestForm);
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("errors", Collections.singleton(e.getMessage()));
            return "redirect:all";
        }
        attributes.addFlashAttribute("success", "Успешно!");
        return "redirect:create";
    }

    @GetMapping("{id}/test/delete")
    @Transactional
    public String deleteTest(@ModelAttribute("model") ModelMap model,
                             @PathVariable("id") Optional<Long> taskId,
                             @RequestParam("testId")Optional<Long> testId,
                             BindingResult errors, RedirectAttributes attributes) {
        try {
            taskService.deleteTest(testId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("errors", Collections.singleton(e.getMessage()));
            return "redirect:all";
        }
        attributes.addFlashAttribute("success", "Успешно!");
        return "redirect:all";
    }
}

package ru.ivmiit.web.controller;

import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.service.ExecutableService;
import ru.ivmiit.web.service.LanguageService;
import ru.ivmiit.web.transfer.ProblemDto;
import ru.ivmiit.web.transfer.SubmissionDto;
import ru.ivmiit.web.validators.SolutionFormValidator;
import ru.ivmiit.web.validators.TaskFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.ProblemService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("problems/")
public class ProblemController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private TaskFormValidator taskFormValidator;

    @Autowired
    private SolutionFormValidator solutionFormValidator;

    @Autowired
    private ExecutableService executableService;

    @Autowired
    private LanguageService languageService;

    @InitBinder("taskForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(taskFormValidator);
    }

    @InitBinder("solutionForm")
    public void initSolutionFormValidator(WebDataBinder binder) {
        binder.addValidators(solutionFormValidator);
    }


    @GetMapping("create")
    public String getTaskPage(@ModelAttribute("model") ModelMap model,
                              @RequestParam("problemId") Optional<Long> problemId,
                              Authentication authentication) {
        authenticationService.putUserToModelIfExists(authentication, model);
        problemId.ifPresent((id) -> model.addAttribute("problem", problemService.getProblemDto(id)));

        model.addAttribute("specialCompare", executableService.getSpecialCompare());
        model.addAttribute("specialRun", executableService.getSpecialRun());
        return "problem/create_problem";
    }

    @PostMapping("create")
    public String createTask(@Valid @ModelAttribute("problemDto") ProblemDto problemDto,
                             BindingResult errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            List<String> errorList = errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            attributes.addFlashAttribute("errors", errorList);
            return "redirect:all";
        }
        problemService.save(problemDto);

        attributes.addFlashAttribute("info", "Успешно!");
        return "redirect:all";
    }

    @GetMapping("delete")
    public String deleteTest(@ModelAttribute("model") ModelMap model,
                             @RequestParam("problemId") Optional<Long> problemId, RedirectAttributes attributes) {
        try {
            problemService.deleteProblem(problemId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("errors", Collections.singleton(e.getMessage()));
            return "redirect:all";
        }
        attributes.addFlashAttribute("success", "Успешно!");
        return "redirect:all";
    }

    @GetMapping("all")
    public String getAllTaskPage(@ModelAttribute("model") ModelMap model, Authentication authentication, @RequestParam("page") Optional<Integer> page) {
        authenticationService.putUserToModelIfExists(authentication, model);
        int currentPage = page.orElse(0);
        model.addAttribute("problems", problemService.getProblemsDto(currentPage));
        model.addAttribute("pageList", problemService.getPageList(currentPage));
        model.addAttribute("currentPage", currentPage);
        return "problem/all_problems";
    }

    @GetMapping("{problemId}")
    public String getTaskPage(@ModelAttribute("model") ModelMap model, Authentication authentication,
                              @PathVariable("problemId") Optional<Long> problemId) {
        authenticationService.putUserToModelIfExists(authentication, model);
        try {
            ProblemDto problem = problemService.getProblemDto(problemId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
            model.addAttribute("problem", problem);
            model.addAttribute("languages", languageService.getLanguages());

        } catch (IllegalArgumentException e) {
            return "redirect:all";
        }
        return "problem/problem_page";
    }

    @PostMapping("{problemId}")
    @PreAuthorize("isAuthenticated()")
    public String createSolution(@Valid @ModelAttribute("solutionForm") SubmissionDto submissionDto,
                                 BindingResult errors,
                                 RedirectAttributes attributes,
                                 Authentication authentication,
                                 @PathVariable("problemId") Optional<Long> problemId) {
        if (errors.hasErrors()) {
            List<String> errorList = errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            attributes.addFlashAttribute("errors", errorList);
            return "redirect:" + (problemId.isPresent() ? problemId.get() : "/");
        }

        submissionDto.setProblemId(problemId.get());
        try {
            problemService.saveSubmission(submissionDto, authenticationService.getUserByAuthentication(authentication));
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        return "redirect:/profile/solutions/all";
    }


}

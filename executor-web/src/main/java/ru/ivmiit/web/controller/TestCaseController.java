package ru.ivmiit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ivmiit.web.model.Problem;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.ProblemService;
import ru.ivmiit.web.transfer.ProblemDto;
import ru.ivmiit.web.transfer.TestCaseDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("problems/")
public class TestCaseController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProblemService problemService;

    @GetMapping("{problemId}/test/all")
    @Transactional
    public String getTestPage(@ModelAttribute("model") ModelMap model, Authentication authentication,
                              @PathVariable("problemId") Optional<Long> problemId) {
        authenticationService.putUserToModelIfExists(authentication, model);
        try {
            Problem problem = problemService.getProblem(problemId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
            model.addAttribute("problem", ProblemDto.from(problem));
            model.addAttribute("testCases", TestCaseDto.from(problem.getTestCases()));
        } catch (IllegalArgumentException e) {
            return "redirect:all";
        }
        return "problem/tests_page";
    }

    @GetMapping("{problemId}/test/create")
    @Transactional
    public String getCreateTestPage(@ModelAttribute("model") ModelMap model,
                                    Authentication authentication,
                                    @PathVariable("problemId") Optional<Long> problemId,
                                    @RequestParam("testId") Optional<Long> testId) {
        authenticationService.putUserToModelIfExists(authentication, model);
        try {
            Problem problem = problemService.getProblem(problemId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
            model.addAttribute("problem", ProblemDto.from(problem));
            testId.ifPresent((id) -> model.addAttribute("test", problemService.getProblemTestCaseDto(id)));
        } catch (IllegalArgumentException e) {
            return "redirect:all";
        }

        return "problem/create_test";
    }

    @PostMapping("{problemId}/test/create")
    @Transactional
    public String createTest(@ModelAttribute("model") ModelMap model,
                             @PathVariable("problemId") Optional<Long> problemId,
                             @ModelAttribute("testCase") TestCaseDto testCaseDto,
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
            problemService.saveProblemTestCase(problemId.orElseThrow(() -> new IllegalArgumentException("Id not found")), testCaseDto);
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("errors", Collections.singleton(e.getMessage()));
            return "redirect:all";
        }
        attributes.addFlashAttribute("success", "Успешно!");
        return "redirect:create";
    }

    @GetMapping("{problemId}/test/delete")
    @Transactional
    public String deleteTest(@ModelAttribute("model") ModelMap model,
                             @PathVariable("problemId") Optional<Long> problemId,
                             @RequestParam("testId")Optional<Long> testId,
                             BindingResult errors, RedirectAttributes attributes) {
        try {
            problemService.deleteTestCase(testId.orElseThrow(() -> new IllegalArgumentException("Id not found")));
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("errors", Collections.singleton(e.getMessage()));
            return "redirect:all";
        }
        attributes.addFlashAttribute("success", "Успешно!");
        return "redirect:all";
    }
}

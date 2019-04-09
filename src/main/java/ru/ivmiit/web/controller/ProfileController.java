package ru.ivmiit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.SolutionService;
import ru.ivmiit.web.utils.TaskUtils;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SolutionService solutionService;

    @GetMapping("/")
    public String getProfilePage(Authentication authentication, @ModelAttribute("model") ModelMap model) {
        authenticationService.putUserToModelIfExists(authentication, model);
        return "profile/profile";
    }

    @GetMapping("/solutions/all")
    public String getProfileSolutionsPage(Authentication authentication, @ModelAttribute("model") ModelMap model,  @RequestParam("page") Optional<Integer> page) {
        authenticationService.putUserToModelIfExists(authentication, model);
        int currentPage = page.orElse(0);
        model.addAttribute("solutions", solutionService.getTasks(currentPage));
        model.addAttribute("pageList", solutionService.getPageList(currentPage));
        model.addAttribute("currentPage", currentPage);
        return "profile/all_solutions";
    }

}

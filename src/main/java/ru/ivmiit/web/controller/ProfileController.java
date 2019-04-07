package ru.ivmiit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.ivmiit.web.service.AuthenticationService;

@Controller
public class ProfileController {
    @Autowired
    private AuthenticationService service;

    @GetMapping("/user/profile")
    public String getProfilePage(Authentication authentication, @ModelAttribute("model") ModelMap model) {
        service.putUserToModelIfExists(authentication, model);
        return "profile/profile";
    }

    @GetMapping("/profile/solutions")
    public String getProfileSolutionsPage(Authentication authentication, @ModelAttribute("model") ModelMap model) {
        service.putUserToModelIfExists(authentication, model);
        return "profile/solutions";
    }

}

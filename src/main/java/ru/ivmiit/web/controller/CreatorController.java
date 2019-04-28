package ru.ivmiit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ivmiit.web.forms.ImportTaskForm;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.PolygonService;

import java.io.IOException;

@Controller
@RequestMapping("/creator")
public class CreatorController {

    @Autowired
    private PolygonService polygonService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    private String mainPage(@ModelAttribute("model") ModelMap model, Authentication authentication) {
        authenticationService.putUserToModelIfExists(authentication, model);
        return "creator/index";
    }

    @GetMapping("/import")
    public String getImportPage(@ModelAttribute("model") ModelMap model, Authentication authentication) {
        authenticationService.putUserToModelIfExists(authentication, model);
        return "creator/polygon_import";
    }

    @PostMapping("/import")
    public String importFromZip(ImportTaskForm importTaskForm,
                                @ModelAttribute("model") ModelMap model,
                                RedirectAttributes attributes) {
        try {
            polygonService.createAndSaveFromPolygonZip(importTaskForm.getFile());
            attributes.addFlashAttribute("info", "Успешно");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/creator/import";

    }
}

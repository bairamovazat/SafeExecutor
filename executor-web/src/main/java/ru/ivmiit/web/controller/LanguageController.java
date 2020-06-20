package ru.ivmiit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ivmiit.web.model.ExecutableType;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.ExecutableService;
import ru.ivmiit.web.service.LanguageService;
import ru.ivmiit.web.transfer.LanguageDto;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/language")
public class LanguageController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private ExecutableService executableService;

    @GetMapping("create")
    public String getLanguagePage(@ModelAttribute("model") ModelMap model,
                                  @RequestParam("languageId") Optional<Long> languageId,
                                  Authentication authentication) {
        authenticationService.putUserToModelIfExists(authentication, model);
        languageId.ifPresent((id) -> model.addAttribute("language", languageService.getLanguageDto(id)));
        model.addAttribute("specialCompile", executableService.getSpecialCompile());

        return "language/create_language";
    }

    @PostMapping("create")
    public String createExecutables(@Valid @ModelAttribute("executableDto") LanguageDto languageDto,
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
            languageService.save(languageDto);
            attributes.addFlashAttribute("info", "Успешно!");
        } catch (Throwable e) {
            log.error("Save executable error", e);
            attributes.addFlashAttribute("errors", Arrays.asList(e.getMessage()));
        }

        return "redirect:create";
    }

    @GetMapping("all")
    public String getAllTaskPage(@ModelAttribute("model") ModelMap model, Authentication authentication,
                                 @RequestParam("page") Optional<Integer> page) {
        authenticationService.putUserToModelIfExists(authentication, model);
        int currentPage = page.orElse(0);
        model.addAttribute("languages", languageService.getLanguagesDto(currentPage));
        model.addAttribute("pageList", languageService.getPageList(currentPage));
        model.addAttribute("currentPage", currentPage);
        return "language/all_languages";
    }
}

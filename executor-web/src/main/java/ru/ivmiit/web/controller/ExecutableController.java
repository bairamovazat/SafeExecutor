package ru.ivmiit.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ivmiit.domjudge.connector.utils.Base64Utils;
import ru.ivmiit.web.model.ExecutableType;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.ExecutableService;
import ru.ivmiit.web.transfer.ExecutableDto;
import ru.ivmiit.web.validators.ExecutableDtoValidator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/executable")
public class ExecutableController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ExecutableService executableService;

    @Autowired
    private ExecutableDtoValidator executableDtoValidator;

    @InitBinder("executableDto")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(executableDtoValidator);
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("create")
    public String getExecutablesPage(@ModelAttribute("model") ModelMap model,
                                     @RequestParam("executableId") Optional<Long> executableId,
                                     Authentication authentication) {
        authenticationService.putUserToModelIfExists(authentication, model);
        executableId.ifPresent((id) -> model.addAttribute("executable", executableService.getExecutableDto(id)));

        model.addAttribute("types", Arrays.asList(ExecutableType.values()));
        return "executable/create_executable";
    }

    @PostMapping("create")
    public String createExecutables(@Valid @ModelAttribute("executableDto") ExecutableDto executableDto,
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
            executableService.save(executableDto);
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
        model.addAttribute("executables", executableService.getExecutablesDto(currentPage));
        model.addAttribute("pageList", executableService.getPageList(currentPage));
        model.addAttribute("currentPage", currentPage);
        return "executable/all_executables";
    }

    @GetMapping("file")
    public void downloadFile(@RequestParam("executableId") Long id, HttpServletResponse response) {
        try {
            executableService.writeExecutableFile(id, response);
        } catch (Exception e) {
            log.error("Write file to response error", e);
        }
    }

    @GetMapping("file-base64")
    @ResponseBody
    public String getFIle(@RequestParam("executableId") Long id, HttpServletResponse response) {
        String base64 = new String(Base64.getUrlEncoder().encode(executableService.getExecutableData(id)));
        return objectMapper.convertValue(base64, String.class);
    }
}

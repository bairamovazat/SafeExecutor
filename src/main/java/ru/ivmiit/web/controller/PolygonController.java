package ru.ivmiit.web.controller;

import org.apache.commons.compress.archivers.ArchiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ivmiit.web.service.AuthenticationService;
import ru.ivmiit.web.service.PolygonService;

import java.io.IOException;

@Controller
@RequestMapping("/polygon")
public class PolygonController {


    @Autowired
    private PolygonService polygonService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/import")
    public String getImportPage(@ModelAttribute("model") ModelMap model, Authentication authentication){
        authenticationService.putUserToModelIfExists(authentication, model);
        return "polygon_import";
    }

    @PostMapping("/import")
    public String  importFromZip(@RequestParam("file") MultipartFile multipartFile,
                                 @ModelAttribute("model") ModelMap model,
                                 RedirectAttributes attributes){
        try {
            polygonService.createTaskFromPolygonZip(multipartFile);
            attributes.addFlashAttribute("info", "Успешно");
        } catch (IOException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:import";

    }
}

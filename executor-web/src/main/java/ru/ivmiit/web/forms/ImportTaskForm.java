package ru.ivmiit.web.forms;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImportTaskForm {
    private MultipartFile file;
}

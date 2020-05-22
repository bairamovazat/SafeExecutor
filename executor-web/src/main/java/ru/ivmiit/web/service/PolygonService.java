package ru.ivmiit.web.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ivmiit.web.model.Problem;

import java.io.IOException;

public interface PolygonService {
    @Transactional
    Problem createAndSaveFromPolygonZip(MultipartFile multipartFile);

    Problem createTaskFromPolygonZip(MultipartFile multipartFile) throws IOException;
}

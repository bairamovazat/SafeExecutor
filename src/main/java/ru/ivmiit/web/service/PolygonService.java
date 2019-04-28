package ru.ivmiit.web.service;

import org.apache.commons.compress.archivers.ArchiveException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ivmiit.web.model.Task;

import java.io.IOException;

public interface PolygonService {
    @Transactional
    Task createAndSaveFromPolygonZip(MultipartFile multipartFile);

    Task createTaskFromPolygonZip(MultipartFile multipartFile) throws IOException;
}

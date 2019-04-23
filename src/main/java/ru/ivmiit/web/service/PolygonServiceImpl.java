package ru.ivmiit.web.service;

import org.apache.commons.compress.archivers.ArchiveException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ivmiit.web.model.Task;
import ru.ivmiit.web.utils.FileUtils;
import ru.ivmiit.web.utils.PolygonXmlParser;
import ru.ivmiit.web.utils.TaskUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

@Service
public class PolygonServiceImpl implements PolygonService {


    @Value("${unzip.dir}")
    private String unzipPolygonDir;

    private static long unzipCount = 0;


    public PolygonServiceImpl(@Value("${unzip.dir}") String unzipPolygonDir){
        File unzipDir = new File(unzipPolygonDir);
        if (!unzipDir.exists()) {
            throw new IllegalArgumentException("Unzip Polygon dir not exist(" + unzipDir + ")!");
        }
//        Arrays.asList(Objects.requireNonNull(unzipDir.listFiles())).forEach(File::delete);
    }

    @Override
    public Task createTaskFromPolygonZip(MultipartFile multipartFile) throws IOException {
        return createTaskFromPolygonZip(multipartFile.getInputStream());
    }

    public Task createTaskFromPolygonZip(InputStream inputStream) throws IOException {
        String unzipDirectoryPath = TaskUtils.getFullPath(unzipPolygonDir) + "/" + unzipCount++;
        File directory = new File(unzipDirectoryPath);
        directory.mkdir();
        FileUtils.unzip(inputStream, unzipDirectoryPath);
        try {
            String rootXmlName = PolygonXmlParser.parseXml(unzipDirectoryPath + "/" + "problem.xml");
        }catch (Exception e){
        }
        return null;
    }

}

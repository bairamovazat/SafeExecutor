package ru.ivmiit.web.service;

import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.transfer.ExecutableDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExecutableService {
    List<ExecutableDto> getSpecialRun();

    List<ExecutableDto> getSpecialCompare();

    @Transactional
    List<ExecutableDto> getSpecialCompile();

    ExecutableDto getExecutableDto(Long id);

    void save(ExecutableDto executableDto);

    @Transactional
    List<ExecutableDto> getExecutablesDto(int page);

    @Transactional
    List<ExecutableDto> getExecutablesDto(int page, int count);

    List<Integer> getPageList(int currentPage);

    void writeExecutableFile(Long id, HttpServletResponse response) throws IOException;

    @Transactional
    byte[] getExecutableData(Long id);
}

package ru.ivmiit.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.ExecutableType;
import ru.ivmiit.web.repository.ExecutableRepository;
import ru.ivmiit.web.transfer.ExecutableDto;
import ru.ivmiit.web.utils.TaskUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExecutableServiceImpl implements ExecutableService {

    private static final int pagesCount = 5;
    private static final int defaultPageElementCount = 10;

    @Autowired
    private ExecutableRepository executableRepository;

    @Override
    @Transactional
    public List<ExecutableDto> getSpecialRun() {
        return ExecutableDto.from(executableRepository.findAllByType(ExecutableType.run));
    }

    @Override
    @Transactional
    public List<ExecutableDto> getSpecialCompare() {
        return ExecutableDto.from(executableRepository.findAllByType(ExecutableType.compare));
    }

    @Override
    @Transactional
    public List<ExecutableDto> getSpecialCompile() {
        return ExecutableDto.from(executableRepository.findAllByType(ExecutableType.compile));
    }

    @Override
    @Transactional
    public ExecutableDto getExecutableDto(Long id) {
        ExecutableDto executableDto = ExecutableDto.from(executableRepository.getOne(id));
        return executableDto;
    }

    @Override
    @Transactional
    public void save(ExecutableDto executableDto) {
        Executable executable;
        if (executableDto.getId() != null) {
            executable = executableRepository.findById(executableDto.getId())
                    .orElse(new Executable());
        } else {
            Optional<Executable> equalNameExecutable = executableRepository.findFirstByName(executableDto.getName());
            if (equalNameExecutable.isPresent()) {
                throw new IllegalArgumentException("Выполняемый файл с таким именем уже есть");
            }
            executable = new Executable();
        }
        executable.setName(executableDto.getName());
        executable.setDescription(executableDto.getDescription());
        executable.setType(executableDto.getType());

        boolean needUpdateFile = executableDto.getUpdateFile() != null && executableDto.getUpdateFile();

        //Если флаг false и объект новый или флаг true, но файла нету
        if ((!needUpdateFile && executable.getId() == null) || (needUpdateFile && executableDto.getFile() == null)) {
            throw new IllegalArgumentException("Обязательно нужно заполнить полей \"Выполняемый файл\"");
        } else if (needUpdateFile) {
            executable.setMultipartFile(executableDto.getFile());
        }
        executableRepository.save(executable);
    }

    @Override
    @Transactional
    public List<ExecutableDto> getExecutablesDto(int page) {
        return getExecutablesDto(page, defaultPageElementCount);
    }

    @Override
    @Transactional
    public List<ExecutableDto> getExecutablesDto(int page, int count) {
        return ExecutableDto.from(executableRepository.findAll(PageRequest.of(page, count)).getContent());
    }

    @Override
    public List<Integer> getPageList(int currentPage) {
        int pageCount = (int) Math.ceil(((double) executableRepository.count()) / pagesCount);
        int maxPage = pageCount - 1;
        return TaskUtils.getPageList(currentPage, pagesCount, maxPage);
    }

    @Override
    @Transactional
    public void writeExecutableFile(Long id, HttpServletResponse response) throws IOException {
        Executable executable = executableRepository.getOne(id);
        response.setContentType(executable.getFileType());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + executable.getFileName() + "\"");
        response.getOutputStream().write(executable.getFileData());
    }

    @Override
    @Transactional
    public byte[] getExecutableData(Long id) {
        Executable executable = executableRepository.getOne(id);
        return executable.getFileData();
    }
}

package ru.ivmiit.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.ExecutableType;
import ru.ivmiit.web.repository.ExecutableRepository;
import ru.ivmiit.web.transfer.ExecutableDto;

import java.util.List;

@Service
@Slf4j
public class ExecutableServiceImpl implements ExecutableService {

    @Autowired
    private ExecutableRepository executableRepository;

    @Override
    public List<ExecutableDto> getSpecialRun() {
        return ExecutableDto.from(executableRepository.findAllByType(ExecutableType.run));
    }

    @Override
    public List<ExecutableDto> getSpecialCompare() {
        return ExecutableDto.from(executableRepository.findAllByType(ExecutableType.compare));
    }
}

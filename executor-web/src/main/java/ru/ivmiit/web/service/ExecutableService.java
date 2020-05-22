package ru.ivmiit.web.service;

import ru.ivmiit.web.transfer.ExecutableDto;

import java.util.List;

public interface ExecutableService {
    List<ExecutableDto> getSpecialRun();

    List<ExecutableDto> getSpecialCompare();
}

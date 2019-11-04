package ru.ivmiit.web.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.model.Solution;

import java.util.List;

public interface SolutionService {
    @Transactional
    List<Solution> getTasks(int page);

    @Transactional
    List<Solution> getTasks(int page, int count);

    @Transactional
    List<Integer> getPageList(int currentPage);

    @Transactional
    List<Integer> getPageList(int currentPage, int count);
}

package ru.ivmiit.web.service;

import ru.ivmiit.web.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.model.Solution;
import ru.ivmiit.web.model.User;
import ru.ivmiit.web.utils.TaskUtils;

import java.util.List;

@Service
public class SolutionServiceImpl implements SolutionService {

    private static int paginationPagesCount = 5;

    private static int defaultElementsInPage = 15;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SolutionRepository solutionRepository;

    @Override
    @Transactional
    public List<Solution> getTasks(int page) {
        return getTasks(page, defaultElementsInPage);
    }

    @Override
    @Transactional
    public List<Solution> getTasks(int page, int count) {
        User user = authenticationService.getCurrentUser();
        return solutionRepository.findAllByAuthorOrderByIdDesc(user, PageRequest.of(page, count)).getContent();
    }

    @Override
    @Transactional
    public List<Integer> getPageList(int currentPage) {
        return getPageList(currentPage, defaultElementsInPage);
    }

    @Override
    @Transactional
    public List<Integer> getPageList(int currentPage, int elementsInPage) {
        User user = authenticationService.getCurrentUser();
        int pageCount = (int) Math.ceil(((double) solutionRepository.countAllByAuthor(user)) / elementsInPage);
        int maxPage = pageCount - 1;
        return TaskUtils.getPageList(currentPage, paginationPagesCount, maxPage);
    }
}

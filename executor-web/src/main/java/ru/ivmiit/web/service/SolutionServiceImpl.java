package ru.ivmiit.web.service;

import ru.ivmiit.web.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.model.Submission;
import ru.ivmiit.web.model.autorization.User;
import ru.ivmiit.web.utils.TaskUtils;

import java.util.List;

@Service
public class SolutionServiceImpl implements SolutionService {

    private static int paginationPagesCount = 5;

    private static int defaultElementsInPage = 15;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    @Transactional
    public List<Submission> getTasks(int page) {
        return getTasks(page, defaultElementsInPage);
    }

    @Override
    @Transactional
    public List<Submission> getTasks(int page, int count) {
        User user = authenticationService.getCurrentUser();
        return submissionRepository.findAllByAuthorOrderByIdDesc(user, PageRequest.of(page, count)).getContent();
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
        int pageCount = (int) Math.ceil(((double) submissionRepository.countAllByAuthor(user)) / elementsInPage);
        int maxPage = pageCount - 1;
        return TaskUtils.getPageList(currentPage, paginationPagesCount, maxPage);
    }
}

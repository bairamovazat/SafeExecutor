package ru.ivmiit.web.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.model.Submission;
import ru.ivmiit.web.transfer.SubmissionDto;

import java.util.List;

public interface SolutionService {
    @Transactional
    List<SubmissionDto> getTasks(int page);

    @Transactional
    List<SubmissionDto> getTasks(int page, int count);

    @Transactional
    List<Integer> getPageList(int currentPage);

    @Transactional
    List<Integer> getPageList(int currentPage, int count);
}

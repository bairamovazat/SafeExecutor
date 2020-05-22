package ru.ivmiit.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ivmiit.web.model.Judging;
import ru.ivmiit.web.model.Submission;
import ru.ivmiit.web.model.autorization.User;

public interface JudgingRepository extends JpaRepository<Judging, Long> {

}

package ru.ivmiit.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.ivmiit.web.model.Submission;
import ru.ivmiit.web.model.SubmissionStatus;
import ru.ivmiit.web.model.autorization.User;

import javax.persistence.LockModeType;

public interface SubmissionRepository extends JpaRepository<Submission, Long>,
        PagingAndSortingRepository<Submission, Long> {

    Page<Submission> findAllByAuthorOrderByIdDesc(User user, Pageable pageable);

    Integer countAllByAuthor(User user);

    Submission findFirstByStatus(SubmissionStatus status);

    @Query("select s from Submission s where s.id = :id")
    @Lock(LockModeType.PESSIMISTIC_READ)
    Submission findFirstByIdAndLock(@Param("id") Long id);
}

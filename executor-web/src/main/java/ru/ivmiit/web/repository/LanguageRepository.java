package ru.ivmiit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivmiit.web.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}

package ru.ivmiit.web.service;

import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.transfer.LanguageDto;

import java.util.List;

public interface LanguageService {
    List<LanguageDto> getLanguages();

    LanguageDto getLanguageDto(Long id);

    void save(LanguageDto languageDto);

    List<LanguageDto> getLanguagesDto(int currentPage);

    @Transactional
    List<LanguageDto> getLanguagesDto(int page, int count);

    Object getPageList(int currentPage);
}

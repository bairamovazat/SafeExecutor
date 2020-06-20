package ru.ivmiit.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.Language;
import ru.ivmiit.web.repository.ExecutableRepository;
import ru.ivmiit.web.repository.LanguageRepository;
import ru.ivmiit.web.transfer.LanguageDto;
import ru.ivmiit.web.utils.TaskUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {


    private static final int pagesCount = 5;
    private static final int defaultPageElementCount = 10;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ExecutableRepository executableRepository;

    @Override
    @Transactional
    public List<LanguageDto> getLanguages() {
        return LanguageDto.from(languageRepository.findAllByAllowSubmit(true));
    }

    @Override
    @Transactional
    public LanguageDto getLanguageDto(Long id) {
        return LanguageDto.from(languageRepository.getOne(id));
    }

    @Override
    @Transactional
    public void save(LanguageDto languageDto) {
        Language language;
        if (languageDto.getId() != null) {
            language = languageRepository.findById(languageDto.getId())
                    .orElse(new Language());
        } else {
            language = new Language();
        }
        language.setName(languageDto.getName());
        language.setExtensions(
                Arrays.stream(languageDto.getExtensions().split(" "))
                        .map(String::trim)
                        .collect(Collectors.toList())
        );
        language.setRequireEntryPoint(languageDto.getRequireEntryPoint());
        language.setEntryPointDescription(languageDto.getEntryPointDescription());
        language.setAllowSubmit(languageDto.getAllowSubmit());
        language.setAllowJudge(languageDto.getAllowJudge());
        language.setTimeFactor(1.0F);

        language.setCompileScript(
                executableRepository.getOne(languageDto.getCompileScript())
        );

        language.setFilterCompilerFiles(languageDto.getFilterCompilerFiles());

        languageRepository.save(language);
    }

    @Override
    @Transactional
    public List<LanguageDto> getLanguagesDto(int page) {
        return getLanguagesDto(page, defaultPageElementCount);
    }

    @Override
    @Transactional
    public List<LanguageDto> getLanguagesDto(int page, int count) {
        return LanguageDto.from(languageRepository.findAll(PageRequest.of(page, count)).getContent());
    }

    @Override
    public List<Integer> getPageList(int currentPage) {
        int pageCount = (int) Math.ceil(((double) languageRepository.count()) / pagesCount);
        int maxPage = pageCount - 1;
        return TaskUtils.getPageList(currentPage, pagesCount, maxPage);
    }
}


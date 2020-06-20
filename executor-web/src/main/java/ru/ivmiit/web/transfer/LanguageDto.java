package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.Language;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageDto {

    public Long id;

    public String name;

    public String extensions;

    private Boolean requireEntryPoint;

    private String entryPointDescription;

    private Boolean allowSubmit;

    private Boolean allowJudge;

    private Long compileScript;

    private String compileScriptName;

    private Boolean filterCompilerFiles;

    public static LanguageDto from(Language language) {
        return LanguageDto.builder()
                .id(language.getId())
                .name(language.getName())
                .extensions(String.join(" ", language.getExtensions()))
                .requireEntryPoint(language.getRequireEntryPoint())
                .entryPointDescription(language.getEntryPointDescription())
                .allowSubmit(language.getAllowSubmit())
                .allowJudge(language.getAllowJudge())
                .compileScript(language.getCompileScript().getId())
                .compileScriptName(language.getCompileScript().getName())
                .filterCompilerFiles(language.getFilterCompilerFiles())
                .build();
    }

    public static List<LanguageDto> from(List<Language> languageList) {
        return languageList.stream()
                .map(LanguageDto::from)
                .collect(Collectors.toList());
    }

}

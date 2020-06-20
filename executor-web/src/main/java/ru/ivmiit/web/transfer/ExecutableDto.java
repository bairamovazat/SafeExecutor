package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.ExecutableType;

import javax.persistence.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutableDto {

    private Long id;

    private String name;

    private String fileName;

    private String fileType;

    private String md5sum;

    private MultipartFile file;

    private String description;

    private ExecutableType type;

    private Boolean updateFile;

    public static ExecutableDto from(Executable executable) {
        return ExecutableDto.builder()
                .id(executable.getId())
                .name(executable.getName())
                .fileName(executable.getFileName())
                .fileType(executable.getFileType())
                .md5sum(executable.getMd5sum())
                .description(executable.getDescription())
                .type(executable.getType())
                .build();
    }


    public static List<ExecutableDto> from(List<Executable> executable) {
        return executable.stream()
                .map(ExecutableDto::from)
                .collect(Collectors.toList());
    }
}

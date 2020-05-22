package ru.ivmiit.web.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ivmiit.web.model.Executable;
import ru.ivmiit.web.model.ExecutableType;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutableDto {

    private Long id;

    private String md5sum;

    private String name;

    private String description;

    private ExecutableType type;

    public static ExecutableDto from(Executable executable) {
        return ExecutableDto.builder()
                .id(executable.getId())
                .md5sum(executable.getMd5sum())
                .name(executable.getName())
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

package ru.ivmiit.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;
import ru.ivmiit.web.utils.EncoderUtils;

import javax.persistence.*;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "executable")
public class Executable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, length = 32)
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "md5_sum", length = 32)
    private String md5sum;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "file_data")
    private String fileData;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "base64_file_data")
    private String base64FileData;

    @Column(name = "description")
    private String description;

    @Column(name = "type", length = 32)
    @Enumerated(EnumType.STRING)
    private ExecutableType type;

    public void setMultipartFile(MultipartFile file) {
        this.setFileName(file.getOriginalFilename());
        this.setFileType(file.getContentType());
        try {
            this.setFileData(new String(file.getBytes()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка получения данных файла формы");
        }
        this.setBase64FileData(EncoderUtils.encodeBase64(this.getFileData()));
        this.setMd5sum(EncoderUtils.getMd5LowerCase(this.getFileData()));
    }
}

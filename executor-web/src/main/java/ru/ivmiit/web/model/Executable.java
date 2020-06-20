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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(name = "description")
    private String description;

    @Column(name = "type", length = 32)
    @Enumerated(EnumType.STRING)
    private ExecutableType type;

    public void setMultipartFile(MultipartFile file) {
        this.setFileName(file.getOriginalFilename());
        this.setFileType(file.getContentType());
        byte[] bytes;
        try {
            bytes = file.getBytes();
            this.setFileData(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка получения данных файла формы");
        }

        String base64 = new String(Base64.getUrlEncoder().encode(bytes));

        this.setMd5sum(EncoderUtils.getMd5LowerCase(bytes));
    }
}

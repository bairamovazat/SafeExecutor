package ru.ivmiit.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "name", length = 32)
    private String name;

    @Column(name = "md5sum", length = 32)
    private String md5sum;

    @Lob
    @Column(name = "zipfile")
    private String zipFile;

    //TODO Only for test
    @Column(name = "data")
    private byte[] data;

    @Column(name = "description")
    private String description;

    @Column(name = "type", length = 32)
    @Enumerated(EnumType.STRING)
    private ExecutableType type;
}

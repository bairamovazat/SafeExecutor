package ru.ivmiit.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "internal_error")
public class InternalError {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "judgehost_log")
    private String judgehostLog;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "disabled")
    private String disabled;
}

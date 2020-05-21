package ru.ivmiit.judging.web.model;

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
@Table(name = "judgehost")
public class JudgeHost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //В домджадже ид - hostname
    @Column(name = "host_name", length = 64)
    private String hostName;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "polltime")
    private Double pollTime;

}

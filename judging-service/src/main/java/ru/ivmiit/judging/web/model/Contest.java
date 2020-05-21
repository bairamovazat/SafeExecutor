package ru.ivmiit.judging.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contest")
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "activate_time")
    private Date activateTime;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "freeze_time")
    private Date freezeTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "unfreeze_time")
    private Date unfreezeTime;

    @Column(name = "deactivate_time")
    private Date deactivateTime;

    @Column(name = "finalizetime")
    private Date finalizeTime;

    @Lob
    @Column(name = "finalizecomment")
    private String finalizeComment;

    @Column(name = "b", columnDefinition = "Number of extra bronze medals")
    private Short b;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "start_time_enabled")
    private Boolean startTimeEnabled;

    @Column(name = "process_balloons")
    private Boolean processBalloons;

    @Column(name = "public")
    private Boolean publicContest;

    @Column(name = "open_to_all_teams")
    private Boolean openToAllTeams;
}

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
@Table(name = "testcase_content")
public class TestCaseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "input")
    private String input;

    @Lob
    @Column(name = "output")
    private String output;

    @Lob
    @Column(name = "image")
    private String image;

    @Lob
    @Column(name = "image_thumb")
    private String imageThumb;
}

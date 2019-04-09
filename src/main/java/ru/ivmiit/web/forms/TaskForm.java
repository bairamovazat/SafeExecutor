package ru.ivmiit.web.forms;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskForm {
    private String name;
    private String description;

    private Integer maxTime;
    private Integer maxRealTime;
    private Integer maxMemory;
    private Integer complexity;

    private String inputDescription;
    private String outputDescription;

    private Long categoryId;

    private List<TaskTestForm> testList = new ArrayList<>();

    public void trim(){
        name = name.trim();
        description = description.trim();
    }
}

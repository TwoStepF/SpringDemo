package com.example.opentalk.dto;

import lombok.Data;

@Data
public class LessonDTO {
    private Long LessonID;

    private String pic;

    private String Name;

    private String content;

    private String level;

    private String time_update;

    private String time_create;

    private int view;

    private boolean save;
}

package com.example.opentalk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOpentalkRequest {
    private Long id;
    private String name;
    private Date time;
}

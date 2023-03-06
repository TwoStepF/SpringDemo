package com.example.opentalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpentalkDTo {
    private Long id;
    private String name;
    private String employeeName;
    private String time;
    private String status;
    private String company_branch_name;
}

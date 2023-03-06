package com.example.opentalk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateEmployeeRequest {
    private String name;
    private String password;
    private String email;
    private String company_branch;
}

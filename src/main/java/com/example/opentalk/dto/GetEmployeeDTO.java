package com.example.opentalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDTO {
    private String name;
    private String role;
    private String hashPassword;
    private Boolean enable;
}

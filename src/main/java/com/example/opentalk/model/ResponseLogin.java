package com.example.opentalk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLogin {
    private HttpStatus status;
    private String message;
    private String authenticationToken;
    private String refreshToken;
    private String username;
    private String role;
}

package com.example.opentalk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TokenToConfirmEmail")
public class TokenToConfirmEmail {

    @Id
    @Column(length = 40)
    private String token_id;

    @OneToOne()
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;
}

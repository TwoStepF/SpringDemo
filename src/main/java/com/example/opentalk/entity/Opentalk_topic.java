package com.example.opentalk.entity;



import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "opentalk_topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Opentalk_topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70)
    @NotBlank
    private String name;

    @Temporal(TemporalType.DATE)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "status_id")
    private Status status;
}

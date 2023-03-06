package com.example.opentalk.entity;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "company_branch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company_branch {
    @Id
    @Column(length = 20)
    private String id;

    @Column(length = 30)
    @NotBlank
    private String branch_Name;

    @Column(length = 30)
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company_branch")
    private Set<Employee> employees = new HashSet<>();
}

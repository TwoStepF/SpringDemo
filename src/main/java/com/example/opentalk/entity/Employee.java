package com.example.opentalk.entity;



import com.example.opentalk.service.AttributeEncryptor;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    @NotBlank
    private String name;

    @Column(length = 200, nullable = false)
    @NotBlank
    private String hashPassword;

    @Column(length = 50)
    @NotBlank
    private String email;

    @Column(length = 20)
    @NotBlank
    @Builder.Default
    private String role = "USER";

    @Column
    @Builder.Default
    private Boolean enable = false;

    @Column
    @Builder.Default
    private String salary = "1000";

    @ManyToOne
    @JoinColumn(name = "Company_ID", nullable = false)
    private Company_branch company_branch;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Set<Opentalk_topic> topics = new HashSet<>();

    @OneToOne(mappedBy = "employee")
    private TokenToConfirmEmail tokenToConfirmEmail;
}

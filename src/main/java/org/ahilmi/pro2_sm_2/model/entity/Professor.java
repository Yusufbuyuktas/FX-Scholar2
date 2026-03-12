package org.ahilmi.pro2_sm_2.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "professors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "professor")
    private List<Teaches> teaches;
}

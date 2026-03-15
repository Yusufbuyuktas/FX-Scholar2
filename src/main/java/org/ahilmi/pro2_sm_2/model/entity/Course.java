package org.ahilmi.pro2_sm_2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "credit")
    private Integer credit;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true) // course silşinidğinde ilgili teach de silinmeli.
    private List<Teaches> teaches;// course hakkında bilgi almak için.

}

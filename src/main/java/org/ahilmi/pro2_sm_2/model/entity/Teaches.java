package org.ahilmi.pro2_sm_2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "teaches")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teaches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Bir profesörün birden fazla ataması olabilir.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    // Bir dersin birden fazla ataması olabilir.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "student_count")
    private Integer studentCount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "ending_date") 
    private LocalDate endingDate;
}
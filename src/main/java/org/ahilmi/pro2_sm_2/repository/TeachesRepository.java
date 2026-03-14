package org.ahilmi.pro2_sm_2.repository;

import org.ahilmi.pro2_sm_2.model.entity.Teaches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachesRepository extends JpaRepository<Teaches, Integer> {


    boolean existsByProfessorIdAndCourseId(Integer professorId, Integer courseId);


}
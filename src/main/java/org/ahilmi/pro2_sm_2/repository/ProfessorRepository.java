package org.ahilmi.pro2_sm_2.repository;


import org.ahilmi.pro2_sm_2.model.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> { // ilk parametre işlem yapacağımız entity'yi belirtir.
                                                                            // ikinci parametre ilgili entity'nin id'sinin (@Id) türünü belirtir.



    boolean existsByName(String name);





}

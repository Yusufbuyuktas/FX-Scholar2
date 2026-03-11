package org.ahilmi.pro2_sm_2.service;

import org.ahilmi.pro2_sm_2.dto.RequestTeachesDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseTeachesDTO;
import org.ahilmi.pro2_sm_2.model.entity.Course;
import org.ahilmi.pro2_sm_2.model.entity.Professor;
import org.ahilmi.pro2_sm_2.model.entity.Teaches;
import org.ahilmi.pro2_sm_2.repository.CourseRepository;
import org.ahilmi.pro2_sm_2.repository.ProfessorRepository;
import org.ahilmi.pro2_sm_2.repository.TeachesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TeachesService {

    private final TeachesRepository teachesRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;

    public TeachesService(TeachesRepository teachesRepository, 
                          ProfessorRepository professorRepository, 
                          CourseRepository courseRepository) {
        this.teachesRepository = teachesRepository;
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
    }

    public String assignProfessorToCourse(RequestTeachesDTO request) {
        
        // 1. Kural: Hoca var mı bak? 
        Professor prof = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Hoca bulunamadı!"));

        // 2. Kural: Ders var mı bak?
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Ders bulunamadı!"));

        // 3. Atama Nesnesini Oluştur (Entity)
        Teaches teaches = new Teaches();
        teaches.setProfessor(prof); // Hocayı bağladık
        teaches.setCourse(course);   // Dersi bağladık
        teaches.setStudentCount(request.getStudentCount());
        teaches.setStartDate(request.getStartDate());
        teaches.setEndingDate(request.getEndingDate());

        // 4. Veritabanına Kaydet
        teachesRepository.save(teaches);

        return "Atama başarıyla yapıldı: " + prof.getName() + " artık " + course.getName() + " dersini veriyor.";
    }
}
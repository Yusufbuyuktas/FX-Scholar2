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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachesService implements ITeachesService {

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

    @Override
    public ResponseTeachesDTO saveTeaches(RequestTeachesDTO request) {
        // 1. İlişkili verileri kontrol et (Hoca ve Ders var mı?)
        Professor prof = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profesör bulunamadı"));
        
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ders bulunamadı"));

        // 2. Entity oluştur ve verileri aktar
        Teaches teaches = new Teaches();
        BeanUtils.copyProperties(request, teaches);
        teaches.setProfessor(prof);
        teaches.setCourse(course);

        // 3. Kaydet ve Response DTO'ya dönüştür
        Teaches dbTeaches = teachesRepository.save(teaches);
        
        return convertToResponseDTO(dbTeaches);
    }

    @Override
    public List<ResponseTeachesDTO> getAllTeaches() {
        List<Teaches> teachesList = teachesRepository.findAll();
        List<ResponseTeachesDTO> responseList = new ArrayList<>();
        
        for (Teaches t : teachesList) {
            responseList.add(convertToResponseDTO(t));
        }
        return responseList;
    }

    @Override
    public ResponseTeachesDTO getTeachesById(Integer id) {
        Teaches teaches = teachesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atama bulunamadı"));
        return convertToResponseDTO(teaches);
    }

    @Override
    public void deleteTeachesById(Integer id) {
        if (!teachesRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Silinecek atama bulunamadı");
        }
        teachesRepository.deleteById(id);
    }

    @Override
    public ResponseTeachesDTO updateTeachesById(Integer id, RequestTeachesDTO request) {
        Teaches dbTeaches = teachesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Güncellenecek atama bulunamadı"));

        // Yeni hoca veya ders atandıysa onları da güncelle
        Professor prof = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yeni profesör bulunamadı"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yeni ders bulunamadı"));

        dbTeaches.setProfessor(prof);
        dbTeaches.setCourse(course);
        dbTeaches.setStudentCount(request.getStudentCount());
        dbTeaches.setStartDate(request.getStartDate());
        dbTeaches.setEndingDate(request.getEndingDate());

        Teaches updated = teachesRepository.save(dbTeaches);
        return convertToResponseDTO(updated);
    }

    // Helper Method: Entity'den DTO'ya dönüşümü tek yerden yapalım (Kod tekrarını önler)
    private ResponseTeachesDTO convertToResponseDTO(Teaches entity) {
        ResponseTeachesDTO dto = new ResponseTeachesDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setProfessorName(entity.getProfessor().getName());
        dto.setCourseName(entity.getCourse().getName());
        return dto;
    }
}
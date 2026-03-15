package org.ahilmi.pro2_sm_2.service;

import org.ahilmi.pro2_sm_2.dto.RequestTeachesDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseTeachesDTO;
import org.ahilmi.pro2_sm_2.exception.ErrorMessages;
import org.ahilmi.pro2_sm_2.exception.ResourceAlreadyExistsException;
import org.ahilmi.pro2_sm_2.exception.ResourceNotFoundException;
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

        if (teachesRepository.existsByProfessorIdAndCourseId(request.getProfessorId(), request.getCourseId())) {
            throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_TEACH_ALREADY_EXIST);
        }

        Professor prof = professorRepository.findById(request.getProfessorId()) // teach içerisinde gelen professor db'de kayıtlı mı
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_PROFESSOR_NOT_FOUND));
        
        Course course = courseRepository.findById(request.getCourseId()) // teach içerisinde gelen course db'de kayıtlı mı
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_COURSE_NOT_FOUND));

        Teaches teaches = new Teaches();
        BeanUtils.copyProperties(request, teaches);
        teaches.setProfessor(prof);
        teaches.setCourse(course);

        // dto döndürmeliyiz
        Teaches dbTeaches = teachesRepository.save(teaches);
        System.out.println("LOG INFO: teach added -> ID: " + dbTeaches.getId());

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
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_TEACH_NOT_FOUND));
        return convertToResponseDTO(teaches);
    }

    @Override
    public void deleteTeachesById(Integer id) {
        if (!teachesRepository.existsById(id)) {
            throw new ResourceNotFoundException(ErrorMessages.ERROR_TEACH_NOT_FOUND);
        }
        teachesRepository.deleteById(id);
        System.out.println("LOG INFO: teach deleted -> ID: " + id);

    }

    @Override
    public ResponseTeachesDTO updateTeachesById(Integer id, RequestTeachesDTO request) {
        Teaches dbTeaches = teachesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_TEACH_NOT_FOUND));

        Professor prof = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_PROFESSOR_NOT_FOUND));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_COURSE_NOT_FOUND));

        dbTeaches.setProfessor(prof);
        dbTeaches.setCourse(course);
        dbTeaches.setStudentCount(request.getStudentCount());
        dbTeaches.setStartDate(request.getStartDate());
        dbTeaches.setEndingDate(request.getEndingDate());

        Teaches updated = teachesRepository.save(dbTeaches);
        System.out.println("LOG INFO: teach updated -> ID: " + updated.getId());

        return convertToResponseDTO(updated);
    }

    // entity'den dto dönüşü tek noktadan yapılır
    private ResponseTeachesDTO convertToResponseDTO(Teaches entity) {
        ResponseTeachesDTO dto = new ResponseTeachesDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setProfessorName(entity.getProfessor().getName());
        dto.setCourseName(entity.getCourse().getName());
        return dto;
    }
}
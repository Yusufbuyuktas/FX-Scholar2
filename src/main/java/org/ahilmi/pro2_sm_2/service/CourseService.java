package org.ahilmi.pro2_sm_2.service;

import org.ahilmi.pro2_sm_2.dto.RequestCourseDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseCourseDTO;
import org.ahilmi.pro2_sm_2.model.entity.Course;
import org.ahilmi.pro2_sm_2.repository.CourseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService{

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository =courseRepository;
    }

    public ResponseCourseDTO saveCourse(RequestCourseDTO requestCourseDTO) {
     ResponseCourseDTO responseCourseDTO = new ResponseCourseDTO(); // response objesi oluşturldu çünkü en son cevap olark vereceğiz
     Course course = new Course();// Db için çünkü dto ile değil entity ile çalışır
     BeanUtils.copyProperties(requestCourseDTO, course); //DTO -> Entity dönüşümü
        Course dbCourse = courseRepository.save(course);// Save method
        BeanUtils.copyProperties(dbCourse, responseCourseDTO); // // entity'deki verileri response DTO'ya aktarır
        return responseCourseDTO;
    }
    public List<ResponseCourseDTO> getAllCourses() {
        List<ResponseCourseDTO> responseCourseDTOList = new ArrayList<>();
         List<Course> courseList = courseRepository.findAll(); // SELECT * FROM course; gibi
         for (Course course : courseList) {
             ResponseCourseDTO responseCourseDTO = new ResponseCourseDTO(); //Veriyi kullanıcıya DTO formatında döndürcez
             BeanUtils.copyProperties(course, responseCourseDTO); // entityden dto ya
             responseCourseDTOList.add(responseCourseDTO); // listeleme :D
         }
         return responseCourseDTOList;
    }
    public ResponseCourseDTO getCourseById(Integer id) {
        ResponseCourseDTO responseCourseDTO = new ResponseCourseDTO();
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Course dbCourse = course.get();
            BeanUtils.copyProperties(dbCourse, responseCourseDTO);
            return responseCourseDTO;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Verilen ID ile eşleşen bir course bulunamadı: " + id);

    }
    public void deleteCourseById(Integer id) {
        Optional<Course> course = courseRepository.findById(id);// checking if there's a course
        if (course.isPresent()) {

            courseRepository.delete(course.get());
            return;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Silinecek course bulunamadı: " + id);
    }
    public ResponseCourseDTO updateCourseById(Integer id, RequestCourseDTO requestCourseDTO) {
        ResponseCourseDTO responseCourseDTO = new ResponseCourseDTO();
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Course dbCourse = course.get();
            dbCourse.setName(requestCourseDTO.getName());
            dbCourse.setCredit(requestCourseDTO.getCredit());
            Course updatedCourse = courseRepository.save(dbCourse);
            BeanUtils.copyProperties(updatedCourse, responseCourseDTO);
            return responseCourseDTO;


        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Verilen ID ile eşleşen bir profesör bulunamadı: " + id);

    }






}

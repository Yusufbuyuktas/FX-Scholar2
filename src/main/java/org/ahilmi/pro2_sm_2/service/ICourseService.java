package org.ahilmi.pro2_sm_2.service;

import org.ahilmi.pro2_sm_2.dto.RequestCourseDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseCourseDTO;

import java.util.List;

public interface ICourseService {

    ResponseCourseDTO saveCourse(RequestCourseDTO requestCourseDTO);
    List<ResponseCourseDTO> getAllCourses();
    ResponseCourseDTO getCourseById(Integer id);
    void deleteCourseById(Integer id);
    ResponseCourseDTO updateCourseById(Integer id, RequestCourseDTO requestCourseDTO);


}

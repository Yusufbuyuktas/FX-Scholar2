package org.ahilmi.pro2_sm_2.controller;


import org.ahilmi.pro2_sm_2.dto.RequestCourseDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseCourseDTO;
import org.ahilmi.pro2_sm_2.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {this.courseService = courseService;}

    @PostMapping(path = "/save")
    public ResponseCourseDTO saveCourse(@RequestBody RequestCourseDTO requestCourseDTO ){
        return courseService.saveCourse(requestCourseDTO);
    }

    @GetMapping(path = "/list")
    public List<ResponseCourseDTO> getAllCourses(){return courseService.getAllCourses();}

    @GetMapping(path = "/list/{id}")
    public ResponseCourseDTO getCourseById(@PathVariable(name = "id") Integer id){
        return courseService.getCourseById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteCourseById(@PathVariable(name = "id") Integer id){courseService.deleteCourseById(id);}

    @PutMapping(path = "/update/{id}")
    public ResponseCourseDTO updateCourseById(@PathVariable(name = "id") Integer id, @RequestBody RequestCourseDTO requestCourseDTO){
        return courseService.updateCourseById(id, requestCourseDTO);
    }
}

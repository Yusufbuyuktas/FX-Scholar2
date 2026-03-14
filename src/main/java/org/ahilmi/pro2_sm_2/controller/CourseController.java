package org.ahilmi.pro2_sm_2.controller;


import org.ahilmi.pro2_sm_2.dto.RequestCourseDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseCourseDTO;
import org.ahilmi.pro2_sm_2.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/course")
public class CourseController {
    private final static Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {this.courseService = courseService;}

    @PostMapping(path = "/save")
    public ResponseEntity<ResponseCourseDTO> saveCourse(@RequestBody RequestCourseDTO requestCourseDTO ){
        ResponseCourseDTO response = courseService.saveCourse(requestCourseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 created dönüyoruz
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<ResponseCourseDTO>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK); // 200 OK dönüyoruz
    }

    @GetMapping(path = "/list/{id}")
    public ResponseEntity<ResponseCourseDTO> getCourseById(@PathVariable(name = "id") Integer id){
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("Get course by id {}", id);
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable(name = "id") Integer id){
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResponseCourseDTO> updateCourseById(@PathVariable(name = "id") Integer id, @RequestBody RequestCourseDTO requestCourseDTO){
        if (id == null || id == 0 || requestCourseDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(courseService.updateCourseById(id, requestCourseDTO), HttpStatus.OK);
    }
}

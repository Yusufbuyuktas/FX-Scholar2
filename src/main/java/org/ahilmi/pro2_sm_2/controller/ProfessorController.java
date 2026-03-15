package org.ahilmi.pro2_sm_2.controller;


import jakarta.validation.Valid;
import org.ahilmi.pro2_sm_2.dto.RequestProfessorDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseCourseDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseProfessorDTO;
import org.ahilmi.pro2_sm_2.service.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// burada bir client'Dan (postman) gelen isteği karşılıyoruz, ardından gerekli işlem ve kontrollerin yapılması için service katmanına yönlendiriyoruz.
@RestController
@RequestMapping("/rest/api/professor")
public class ProfessorController {
    private final static Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {this.professorService = professorService;}


    @PostMapping(path = "/save")
    public ResponseEntity<ResponseProfessorDTO> saveProfessor(@Valid @RequestBody RequestProfessorDTO requestProfessorDTO) {
        ResponseProfessorDTO response = professorService.saveProfessor(requestProfessorDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 created dönüyoruz
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<ResponseProfessorDTO>> getAllProfessor() {
        return new ResponseEntity<>(professorService.getAllProfessor(), HttpStatus.OK);
    }


    @GetMapping(path = "/list/{id}") // ---> bu satırdaki {...} ile parametrede name = "..." alanına verdiğim değer aynı olmalı.
    public ResponseEntity<ResponseProfessorDTO> getProfessorById(@PathVariable(name = "id") Integer id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("Get professor by id {}", id);
        return new ResponseEntity<>(professorService.getProfessorById(id), HttpStatus.OK);
    }


    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable(name = "id") Integer id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        professorService.deleteProfessor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResponseProfessorDTO> updateProfessor(@PathVariable(name = "id") Integer id, @Valid @RequestBody RequestProfessorDTO requestProfessorDTO) {
        if (id == null || id == 0 || requestProfessorDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(professorService.updateProfessor(id, requestProfessorDTO), HttpStatus.OK);
    }

}

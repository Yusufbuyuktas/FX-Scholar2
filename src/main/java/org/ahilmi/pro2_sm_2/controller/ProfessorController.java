package org.ahilmi.pro2_sm_2.controller;


import org.ahilmi.pro2_sm_2.dto.RequestProfessorDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseProfessorDTO;
import org.ahilmi.pro2_sm_2.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// burada bir client'Dan (postman) gelen isteği karşılıyoruz, ardından gerekli işlem ve kontrollerin yapılması için service katmanına yönlendiriyoruz.
@RestController
@RequestMapping("/rest/api/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;


    @PostMapping(path = "/save")
    public ResponseProfessorDTO saveProfessor(@RequestBody RequestProfessorDTO requestProfessorDTO) {
        return professorService.saveProfessor(requestProfessorDTO);

    }

    @GetMapping(path = "/list")
    public List<ResponseProfessorDTO> getAllProfessor() {
        return professorService.getAllProfessor();
    }


    @GetMapping(path = "/list/{id}") // ---> bu satırdaki {...} ile parametrede name = "..." alanına verdiğim değer aynı olmalı.
    public ResponseProfessorDTO getProfessorById(@PathVariable(name = "id") Integer id) {
        return professorService.getProfessorById(id);
    }


    @DeleteMapping(path = "/delete/{id}")
    public void deleteProfessor(@PathVariable(name = "id") Integer id) {
        professorService.deleteProfessor(id);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseProfessorDTO updateProfessor(@PathVariable(name = "id") Integer id, @RequestBody RequestProfessorDTO requestProfessorDTO) {
        return professorService.updateProfessor(id, requestProfessorDTO);
    }



}

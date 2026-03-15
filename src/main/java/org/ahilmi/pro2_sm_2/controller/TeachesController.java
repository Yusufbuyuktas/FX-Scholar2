package org.ahilmi.pro2_sm_2.controller;

import jakarta.validation.Valid;
import org.ahilmi.pro2_sm_2.dto.RequestTeachesDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseTeachesDTO;
import org.ahilmi.pro2_sm_2.service.ITeachesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/teaches")
public class TeachesController {


    private final ITeachesService teachesService;

    public TeachesController(ITeachesService teachesService) {
        this.teachesService = teachesService;
    }


    @PostMapping("/save")
    public ResponseEntity<ResponseTeachesDTO> saveTeaches(@Valid @RequestBody RequestTeachesDTO request) {
        ResponseTeachesDTO response = teachesService.saveTeaches(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ResponseTeachesDTO>> getAllTeaches() {
        return ResponseEntity.ok(teachesService.getAllTeaches());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseTeachesDTO> getTeachesById(@PathVariable Integer id) {
        return ResponseEntity.ok(teachesService.getTeachesById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseTeachesDTO> updateTeaches(@PathVariable Integer id, @Valid @RequestBody RequestTeachesDTO request) {
        return ResponseEntity.ok(teachesService.updateTeachesById(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeaches(@PathVariable Integer id) {
        teachesService.deleteTeachesById(id);
        return ResponseEntity.ok().build();
    }
}
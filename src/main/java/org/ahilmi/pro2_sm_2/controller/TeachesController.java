package org.ahilmi.pro2_sm_2.controller;

import org.ahilmi.pro2_sm_2.dto.RequestTeachesDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseTeachesDTO;
import org.ahilmi.pro2_sm_2.service.ITeachesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/teaches")
public class TeachesController {

    // Artık Interface üzerinden çağırıyoruz (Loose Coupling)
    private final ITeachesService teachesService;

    public TeachesController(ITeachesService teachesService) {
        this.teachesService = teachesService;
    }

    // 1. Yeni Atama Yap (Save)
    @PostMapping("/save")
    public ResponseEntity<ResponseTeachesDTO> saveTeaches(@RequestBody RequestTeachesDTO request) {
        ResponseTeachesDTO response = teachesService.saveTeaches(request);
        return ResponseEntity.ok(response);
    }

    // 2. Tüm Atamaları Listele (Get All)
    @GetMapping("/list")
    public ResponseEntity<List<ResponseTeachesDTO>> getAllTeaches() {
        return ResponseEntity.ok(teachesService.getAllTeaches());
    }

    // 3. ID ile Atama Getir (Get By ID)
    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseTeachesDTO> getTeachesById(@PathVariable Integer id) {
        return ResponseEntity.ok(teachesService.getTeachesById(id));
    }

    // 4. Atamayı Güncelle (Update)
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseTeachesDTO> updateTeaches(@PathVariable Integer id, @RequestBody RequestTeachesDTO request) {
        return ResponseEntity.ok(teachesService.updateTeachesById(id, request));
    }

    // 5. Atamayı Sil (Delete)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeaches(@PathVariable Integer id) {
        teachesService.deleteTeachesById(id);
        return ResponseEntity.ok().build(); // 200 OK döner ama gövdesi boştur
    }
}
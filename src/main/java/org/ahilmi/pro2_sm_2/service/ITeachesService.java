package org.ahilmi.pro2_sm_2.service;

import org.ahilmi.pro2_sm_2.dto.RequestTeachesDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseTeachesDTO;

import java.util.List;

public interface ITeachesService {
    
    // Yeni bir atama (hoca-ders eşleşmesi) kaydeder
    ResponseTeachesDTO saveTeaches(RequestTeachesDTO requestTeachesDTO);

    // Tüm atamaları listeler
    List<ResponseTeachesDTO> getAllTeaches();

    // ID'ye göre tek bir atama getirir
    ResponseTeachesDTO getTeachesById(Integer id);

    // Bir atamayı siler
    void deleteTeachesById(Integer id);

    // Mevcut atamayı günceller
    ResponseTeachesDTO updateTeachesById(Integer id, RequestTeachesDTO requestTeachesDTO);
}
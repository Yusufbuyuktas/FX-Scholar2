package org.ahilmi.pro2_sm_2.service;

import org.ahilmi.pro2_sm_2.dto.RequestProfessorDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseProfessorDTO;

import java.util.List;

public interface IProfessorService {

    ResponseProfessorDTO saveProfessor(RequestProfessorDTO requestProfessorDTO);
    List<ResponseProfessorDTO> getAllProfessor();
    ResponseProfessorDTO getProfessorById(Integer id);
    void deleteProfessor(Integer id);
    ResponseProfessorDTO updateProfessor(Integer id, RequestProfessorDTO requestProfessorDTO);
}

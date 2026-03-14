package org.ahilmi.pro2_sm_2.service;


import org.ahilmi.pro2_sm_2.dto.RequestProfessorDTO;
import org.ahilmi.pro2_sm_2.dto.ResponseProfessorDTO;
import org.ahilmi.pro2_sm_2.exception.ErrorMessages;
import org.ahilmi.pro2_sm_2.exception.ResourceAlreadyExistsException;
import org.ahilmi.pro2_sm_2.exception.ResourceNotFoundException;
import org.ahilmi.pro2_sm_2.model.entity.Professor;
import org.ahilmi.pro2_sm_2.repository.ProfessorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.ahilmi.pro2_sm_2.dto.ResponseTeachesDTO;
import org.ahilmi.pro2_sm_2.model.entity.Teaches;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService implements IProfessorService{

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository){
        this.professorRepository =professorRepository;
    }

    public ResponseProfessorDTO saveProfessor(RequestProfessorDTO requestProfessorDTO) {
        if (professorRepository.existsByName(requestProfessorDTO.getName())) {
            throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_PROFESSOR_ALREADY_EXIST);
        }

        ResponseProfessorDTO response = new ResponseProfessorDTO();
        Professor professor = new Professor();
        BeanUtils.copyProperties(requestProfessorDTO, professor); // 1. parametre --> source, 2. parametre --> target
        // parametreden gelen dto türünde aldığım veriyi, professor türüne (entity) çeviriyorum. çünkü db'ye kaydetmem lazım.

        Professor dbProfessor = professorRepository.save(professor); // jparepository'nin sağladığı save metodunu kullandık. save doğrudan entity nesneleri üzerinde çalışır.
        BeanUtils.copyProperties(dbProfessor, response);
        return response; // kullanıcıya responsedto türünde bir sonuç göstermem lazım. bunun için dbPRofessor'ı response'a çevirdim.
    }


    public List<ResponseProfessorDTO> getAllProfessor() {
        List<ResponseProfessorDTO> responseList = new ArrayList<>();

        List<Professor> professorList = professorRepository.findAll(); // jparepository'nin sağladığı findAll metodunu kullanıyorum. db'den entity biçiminde alıyorum.
        for (Professor professor : professorList) {
            ResponseProfessorDTO response = new ResponseProfessorDTO();
            BeanUtils.copyProperties(professor, response); // veri asla entity biçiminde döndürülmemeli. dto türünde olmalı. bu yüzden db'den gelen entity türünü dto'ya çevirdim.
            responseList.add(response);
        }
        return responseList;
    }


    public ResponseProfessorDTO getProfessorById(Integer id) {
        ResponseProfessorDTO response = new ResponseProfessorDTO();
        Optional<Professor> professor = professorRepository.findById(id); // db'den prof'u çektim

        if (professor.isPresent()) {
            Professor dbProf = professor.get();
            BeanUtils.copyProperties(dbProf, response);

            List<ResponseTeachesDTO> teachesList = new ArrayList<>();

            if (dbProf.getTeaches() != null) { // bulduğumuz prof'un verdiği course'ları dto'ya çevirip kullanıcıya döneceğiz.
                for (Teaches teaches : dbProf.getTeaches()) {  // prof'un verdiği course'lar bir liste formatında döndüğü için foreach kullanarak her bir course'u dto'ya çeviriyoruz.
                    ResponseTeachesDTO teachesDTO = new ResponseTeachesDTO();
                    teachesDTO.setId(teaches.getId());
                    teachesDTO.setProfessorName(teaches.getProfessor().getName());
                    teachesDTO.setCourseName(teaches.getCourse().getName());
                    teachesDTO.setStudentCount(teaches.getStudentCount());
                    teachesDTO.setStartDate(teaches.getStartDate());
                    teachesDTO.setEndingDate(teaches.getEndingDate());

                    teachesList.add(teachesDTO);
                }
            }

            response.setTeaches(teachesList);
            return response;
        }

        throw new ResourceNotFoundException(ErrorMessages.ERROR_PROFESSOR_NOT_FOUND + " : " + id);
    }

    public void deleteProfessor(Integer id) {
        Optional<Professor> professor = professorRepository.findById(id);

        if (professor.isPresent()) {
            professorRepository.delete(professor.get()); // delete, bir entity bekler.
            return;
        }

        throw new ResourceNotFoundException(ErrorMessages.ERROR_PROFESSOR_NOT_FOUND + " : " + id);
    }


    public ResponseProfessorDTO updateProfessor(Integer id, RequestProfessorDTO requestProfessorDTO) {
        ResponseProfessorDTO response = new ResponseProfessorDTO();
        Optional<Professor> professor = professorRepository.findById(id);

        if (professor.isPresent()) {
            Professor dbProfessor = professor.get();

            dbProfessor.setName(requestProfessorDTO.getName());
            dbProfessor.setDepartment(requestProfessorDTO.getDepartment());

            Professor updatedProfessor = professorRepository.save(dbProfessor);  // save, işlem yaparken eğer eşleşen kayıt varsa kaydın üzerine yazar. bu sebeple güncelleme için kullanabiliriz.
            BeanUtils.copyProperties(updatedProfessor, response); // kullanıcı güncellediği kaydın son versiyonunun görmeli. bunun için de dto (response) türünde döndürmeliyiz.

            return response;
        }

        throw new ResourceNotFoundException(ErrorMessages.ERROR_PROFESSOR_NOT_FOUND + ": " + id);
    }

}
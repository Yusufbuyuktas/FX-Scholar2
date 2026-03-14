package org.ahilmi.pro2_sm_2.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RequestTeachesDTO {
    private Integer professorId; 
    private Integer courseId;    
    private Integer studentCount;
    private LocalDate startDate;
    private LocalDate endingDate;
}
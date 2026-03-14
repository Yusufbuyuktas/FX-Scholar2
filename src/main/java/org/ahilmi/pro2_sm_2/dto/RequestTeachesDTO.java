package org.ahilmi.pro2_sm_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RequestTeachesDTO {

    @NotNull(message = "professor id cannot be null")
    private Integer professorId;
    @NotNull(message = "course id cannot be null")
    private Integer courseId;
    private Integer studentCount;
    @NotNull(message = "start date cannot be null")
    private LocalDate startDate;
    private LocalDate endingDate;
}
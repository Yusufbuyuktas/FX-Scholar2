package org.ahilmi.pro2_sm_2.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCourseDTO {

    /*
    kullanıcı bir istek attığında (insert, update) aşağıdaki alanlara ait bilgileri bize vermeli.
    id yok çünkü db tarafından otomatik veriliyor.
    */

    @NotBlank(message = "name field cannot be null")
    private String name;

    @NotBlank(message = "credit field cannot be null")
    private Integer credit;

}
